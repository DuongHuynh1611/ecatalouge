package vn.dtpsoft.modules.user.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateUserForm {

    private Integer id;

    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String firstName;

    @NotBlank
    private String password;

    private String lastName;

    @NotEmpty
    List<Integer> roleIds;

}
