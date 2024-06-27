package vn.dtpsoft.modules.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.dtpsoft.modules.post.form.CreatePostForm;
import vn.dtpsoft.modules.post.form.UpdatePostForm;
import vn.dtpsoft.modules.postCatefory.PostCategory;
import vn.dtpsoft.modules.postCatefory.PostCategoryService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostCategoryService postCategoryService;

    @GetMapping()
    List<Post> finAllPost(){
        return postService.finAll();
    }

    @GetMapping("/{id}")
    private Object getByPostId(@PathVariable(value = "id") Integer id) {
        return postService.findById(id);
    }

    @PostMapping()
    private Object createPost(@Valid @ModelAttribute CreatePostForm createPostForm,@RequestParam("thumbnail") MultipartFile thumbnail)throws IOException{
        Post post = new Post();
        String thumbnails = postService.saveThumbnailFile(thumbnail);
        post.setTitle(createPostForm.getTitle());
        post.setContent(createPostForm.getContent());
        PostCategory postCategory = postCategoryService.findById(createPostForm.getCategoryId());
        post.setPostCategory(postCategory);
        post.setThumbnail(thumbnails);
        postService.savePost(post);
        return post;
    }


    @PutMapping("/{id}")
    private Object updatePost(@Valid @ModelAttribute UpdatePostForm updatePostForm,@RequestParam("thumbnail") MultipartFile thumbnail)throws IOException{
        Post post =postService.findById(updatePostForm.getId());
        String thumbnails = postService.saveThumbnailFile(thumbnail);
        post.setTitle(updatePostForm.getTitle());
        post.setContent(updatePostForm.getContent());
        PostCategory postCategory = postCategoryService.findById(updatePostForm.getCategoryId());
        post.setPostCategory(postCategory);
        post.setThumbnail(thumbnails);
        return postService.savePost(post);
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable(value = "id") Integer id) {
        postService.deleteById(id);
        //return "Delete ok!";

    }
}
