package vn.dtpsoft.modules.post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dtpsoft.exception.EntityNotFoundException;

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
}
