package vn.dtpsoft.modules.postCatefory.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryForm extends CreateCategoryForm{
    @NotNull
    private Integer id;
}
