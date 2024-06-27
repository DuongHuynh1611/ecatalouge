package vn.dtpsoft.modules.comment.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentForm {
    @NotNull
    private Integer postId;

    private String content;
}
