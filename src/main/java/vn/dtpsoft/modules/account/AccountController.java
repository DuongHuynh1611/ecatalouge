package vn.dtpsoft.modules.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.dtpsoft.exception.BadRequestException;
import vn.dtpsoft.model.dto.ApiMessageDto;
import vn.dtpsoft.modules.BaseController;
import vn.dtpsoft.modules.account.form.UpdateAvatarForm;
import vn.dtpsoft.modules.account.form.UpdateProfile;
import vn.dtpsoft.modules.user.User;
import vn.dtpsoft.modules.user.UserRepository;
import vn.dtpsoft.modules.user.UserService;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
    public ApiMessageDto<Object> updateProfile(@Valid @RequestBody UpdateProfile updateProfile){
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

//    @PutMapping("/avatar")
//    public Object updateAvatar(@RequestParam("avatar") MultipartFile avatar) {
//        // Lấy thông tin người dùng hiện tại từ SecurityContext
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();  // Lấy username của người dùng hiện tại
//        User user = userRepository.getUserByUsername(username);  // Lấy thông tin user từ cơ sở dữ liệu dựa trên username
//
//        if (avatar.isEmpty()) {
//            throw new BadRequestException("File avatar trống!");  // Ném ra ngoại lệ nếu file avatar trống
//        }
//
//        String avatarUrl = userService.storeFile(avatar);  // Lưu file avatar và lấy đường dẫn hoặc URL của file
//
//        user.setAvatar(avatarUrl);  // Cập nhật đường dẫn hoặc URL của avatar mới cho user
//        userRepository.save(user);  // Lưu thông tin user đã cập nhật vào cơ sở dữ liệu
//
//        return makeResponse(true, user, "Avatar cập nhật thành công!!");  // Trả về phản hồi thành công
//    -
    @PutMapping("/update-avatar")
    public ApiMessageDto<Object> updateAvatar(@RequestParam("avatar") MultipartFile avatar) throws IOException {
        String avatarUrl = userService.saveAvatarFile(avatar);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);
        return makeResponse(true, user, "Update avatar success!!");
    }
}

