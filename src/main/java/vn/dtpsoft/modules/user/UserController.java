package vn.dtpsoft.modules.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.dtpsoft.exception.BadRequestException;
import vn.dtpsoft.modules.BaseController;
import vn.dtpsoft.modules.role.Role;
import vn.dtpsoft.modules.role.RoleRepository;
import vn.dtpsoft.modules.role.RoleService;
import vn.dtpsoft.modules.user.form.CreateUserForm;
import vn.dtpsoft.modules.user.form.UpdateUserForm;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @PostMapping()
    public Object createUser(@Valid @RequestBody CreateUserForm createForm){
        if(userService.existsByPhone(createForm.getPhone())) {
            throw new BadRequestException("SDT da ton tai");
        }
        else if (userService.existsByEmail(createForm.getEmail())){
            throw new BadRequestException("Email da ton tai");
        }else if (userService.existsByUserName(createForm.getUsername())){
            throw new BadRequestException("Username da ton tai");
        }
        User user = new User();
        user.setId(createForm.getId());
        user.setEmail(createForm.getEmail());
        user.setPhone(createForm.getPhone());
        user.setPassword(passwordEncoder.encode(createForm.getPassword()));
        user.setUsername(createForm.getUsername());
        user.setFirstName(createForm.getFirstName());
        user.setLastName(createForm.getLastName());
        user.setActive(true);
        List<Role> roles = roleService.findByIdIn(createForm.getRoleIds());
        user.setRoles(roles);
        userService.saveUser(user);

        return makeResponse(true, user, "Create user successful!");
    }

    @PutMapping()
    public Object updateUser(@Valid @RequestBody UpdateUserForm updateForm){
        User user = userService.findById(updateForm.getId());
        user.setEmail(updateForm.getEmail());
        user.setPhone(updateForm.getPhone());
        user.setFirstName(updateForm.getFirstName());
        user.setLastName(updateForm.getLastName());
        if (updateForm.getRoleIds() == null || updateForm.getRoleIds().isEmpty()) {
            user.setRoles(null);
        } else {
            List<Role> roles = roleService.findByIdIn(updateForm.getRoleIds());
            user.setRoles(roles);
        }
        return userService.saveUser(user);
    }

    @GetMapping()
    List<User> finAllUser(){
        return userService.finAll();
    }
    @GetMapping("/{id}")
    private Object getById(@PathVariable(value = "id") Integer id) {
        User user = userService.findById(id);
       return user;
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable(value = "id") Integer id) {
       User user = userService.deleteById(id);
    }
}
