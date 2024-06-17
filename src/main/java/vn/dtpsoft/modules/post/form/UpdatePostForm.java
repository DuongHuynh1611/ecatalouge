package vn.dtpsoft.modules.post.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePostForm extends CreatePostForm {
    @NotNull
    private Integer id;
}
