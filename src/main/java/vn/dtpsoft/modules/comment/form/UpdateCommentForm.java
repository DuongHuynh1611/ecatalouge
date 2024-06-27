package vn.dtpsoft.modules.comment.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCommentForm {
    @NotNull
    private Integer id;
    private String content;
}
