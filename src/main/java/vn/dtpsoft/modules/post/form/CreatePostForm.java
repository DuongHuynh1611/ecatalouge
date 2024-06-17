package vn.dtpsoft.modules.post.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class CreatePostForm {
    @NotNull
    private Integer categoryId;

    @NotBlank
    private String title;

    private String content;
}
