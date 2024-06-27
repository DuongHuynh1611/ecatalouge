package vn.dtpsoft.modules.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dtpsoft.exception.EntityNotFoundException;
import vn.dtpsoft.modules.post.Post;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> finAll(){
        return commentRepository.findAll();
    }

    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException( Post.class, String.valueOf(id)));
    }

    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }
}
