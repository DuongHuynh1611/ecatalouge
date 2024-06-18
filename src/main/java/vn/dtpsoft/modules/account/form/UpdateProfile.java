package vn.dtpsoft.modules.account.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateProfile {
    private String firstName;
    private String lastName;
    @NotBlank(message = "Password is required")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    private String newPassword;
}
