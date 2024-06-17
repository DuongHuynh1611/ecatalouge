package vn.dtpsoft.modules.postCatefory.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCategoryForm {

    @NotBlank
    private String name;

}
