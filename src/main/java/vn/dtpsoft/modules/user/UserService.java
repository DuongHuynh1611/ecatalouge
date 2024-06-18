package vn.dtpsoft.modules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.dtpsoft.exception.EntityNotFoundException;
import vn.dtpsoft.modules.role.RoleRepository;

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
}