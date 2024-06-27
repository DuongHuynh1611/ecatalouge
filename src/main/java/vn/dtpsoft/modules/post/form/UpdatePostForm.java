package vn.dtpsoft.modules.post.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePostForm extends CreatePostForm {
    @NotNull
    private Integer id;

    private MultipartFile thumbnail;
}
