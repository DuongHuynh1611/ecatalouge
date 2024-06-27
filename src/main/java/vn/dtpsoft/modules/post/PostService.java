package vn.dtpsoft.modules.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.dtpsoft.exception.EntityNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> finAll(){
        return postRepository.findAll();
    }


    public Post findById(Integer id) {
        return postRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException( Post.class, String.valueOf(id)));
    }

    public void deleteById(int id) {
        postRepository.deleteById(id);
    }

    public String saveThumbnailFile(MultipartFile thumbnail) throws IOException {
        // Xử lý upload file và lưu trữ file vào thư mục thích hợp
        // Ví dụ: sử dụng FileCopyUtils để copy file vào thư mục
        File dests = new File("contents/thumbnails/" + thumbnail.getOriginalFilename());
        FileCopyUtils.copy(thumbnail.getInputStream(), Files.newOutputStream(dests.toPath()));
        return "/thumbnails/" + thumbnail.getOriginalFilename();
    }
}
