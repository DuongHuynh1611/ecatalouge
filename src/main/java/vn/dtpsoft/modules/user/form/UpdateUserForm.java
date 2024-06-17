package vn.dtpsoft.modules.user.form;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class UpdateUserForm {
    @NotNull
    private Integer id;

    @Email
    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    List<Integer> roleIds;

}
