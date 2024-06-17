package vn.dtpsoft.modules.account.form;
import vn.dtpsoft.modules.role.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignupForm  {
    @NotBlank
    private String firstName;

    private String lastName;
    @NotBlank
    private String phone;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private Role role;

}
