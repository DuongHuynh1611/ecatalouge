package vn.dtpsoft.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.dtpsoft.exception.EntityNotFoundException;
import vn.dtpsoft.modules.role.RoleRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> finAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(User.class, String.valueOf(id.getClass())));
    }

    public User deleteById(int id) {
        userRepository.deleteById(id);
        Iterator<User> iterator = userRepository.findAll().iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }

    public boolean existsByPhone(String phone) {
        UserRepository a = userRepository;
        return userRepository.existsByPhone(phone);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

//    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
//
//    public void FileStorageService(){
//        try {
//            Files.createDirectories(this.fileStorageLocation);  // Tạo thư mục nếu chưa tồn tại
//        } catch (Exception ex) {
//            throw new RuntimeException("Không thể tạo thư mục để lưu trữ file tải lên.", ex);  // Ném ra ngoại lệ nếu có lỗi khi tạo thư mục
//        }
//    }
//
//    public String storeFile(MultipartFile file) {
//        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();  // Tạo tên file duy nhất
//        try {
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);  // Xác định vị trí lưu file
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);  // Lưu file vào vị trí đã xác định
//            return targetLocation.toString();  // Trả về đường dẫn của file đã lưu
//        } catch (Exception ex) {
//            throw new RuntimeException("Không thể lưu file " + fileName + ". Vui lòng thử lại!", ex);  // Ném ra ngoại lệ nếu có lỗi khi lưu file
//        }
//    }

    public String saveAvatarFile(MultipartFile avatar) throws IOException {
        File dest = new File("contents/avatars/" + avatar.getOriginalFilename());
        FileCopyUtils.copy(avatar.getInputStream(), Files.newOutputStream(dest.toPath()));
        return "/avatars/" + avatar.getOriginalFilename();
    }
}