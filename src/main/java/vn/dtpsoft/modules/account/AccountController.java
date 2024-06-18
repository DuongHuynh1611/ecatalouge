package vn.dtpsoft.modules.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.dtpsoft.exception.BadRequestException;
import vn.dtpsoft.modules.BaseController;
import vn.dtpsoft.modules.account.form.UpdateProfile;
import vn.dtpsoft.modules.user.User;
import vn.dtpsoft.modules.user.UserRepository;
import vn.dtpsoft.modules.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public User getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        return user;
    }

    @PutMapping("/profile")
    public Object updateProfile(@Valid @RequestBody UpdateProfile updateProfile){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        user.setFirstName(updateProfile.getFirstName());
        user.setLastName(updateProfile.getLastName());
        if(updateProfile.getNewPassword()!=null && updateProfile.getOldPassword()!= null){
            if(!passwordEncoder.matches(updateProfile.getOldPassword(),user.getPassword())){
                throw new BadRequestException("Password cu ko dung!");
            }
            else if(passwordEncoder.matches(updateProfile.getNewPassword(),user.getPassword())){
                throw new BadRequestException("NewPassWord ko đc trùng OldPassWord!");
            }
            user.setPassword(passwordEncoder.encode(updateProfile.getNewPassword()));
            userRepository.save(user);
        }
        return makeResponse(true,user,"Update success!!");
    }
}
