package vn.dtpsoft.modules.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.dtpsoft.exception.BadRequestException;
import vn.dtpsoft.model.dto.ApiMessageDto;
import vn.dtpsoft.modules.BaseController;
import vn.dtpsoft.modules.comment.form.CreateCommentForm;
import vn.dtpsoft.modules.comment.form.UpdateCommentForm;
import vn.dtpsoft.modules.post.Post;
import vn.dtpsoft.modules.post.PostRepository;
import vn.dtpsoft.modules.post.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;


    @GetMapping()
    List<Comment> findAllComment(){
        return commentService.finAll();
    }

    @GetMapping("/posts/{postId}")
    private ApiMessageDto<Object> getAllCommentsByPostId(@PathVariable(value = "postId") Integer postId){
        if (!postRepository.existsById(postId)){
            throw new BadRequestException("Khong tim thay Id= "+postId);
        }
        List<Comment> comment = commentRepository.findByPostId(postId);
        return  makeResponse(true,comment,"Get comment successful!");
    }

    @PostMapping
    private ApiMessageDto<Object> createComment(@Valid @RequestBody CreateCommentForm createCommentForm){
        Comment comment = new Comment();
        comment.setContent(createCommentForm.getContent());
        Post post = postService.findById(createCommentForm.getPostId());
        comment.setPost(post);
        commentService.saveComment(comment);

        return  makeResponse(true,comment,"Create comment successful!");
    }

    @PutMapping
    private ApiMessageDto<Object> updateComment(@Valid @RequestBody UpdateCommentForm updateCommentForm){
        Comment comment = commentService.findById(updateCommentForm.getId());
        comment.setContent(updateCommentForm.getContent());
        return makeResponse(true,comment,"Update Comment successful!");
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable(value = "id") Integer id){
        commentService.deleteById(id);
    }
}
