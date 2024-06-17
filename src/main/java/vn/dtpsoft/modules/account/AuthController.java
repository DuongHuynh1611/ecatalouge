package vn.dtpsoft.modules.account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.dtpsoft.constant.ERole;
import vn.dtpsoft.exception.BadRequestException;
import vn.dtpsoft.model.dto.ApiMessageDto;
import vn.dtpsoft.modules.BaseController;
import vn.dtpsoft.modules.account.dto.TokenAuthDto;
import vn.dtpsoft.modules.account.form.LoginForm;
import vn.dtpsoft.modules.account.form.SignupForm;
import vn.dtpsoft.modules.role.Role;
import vn.dtpsoft.modules.role.RoleRepository;
import vn.dtpsoft.modules.user.User;
import vn.dtpsoft.modules.user.UserRepository;
import vn.dtpsoft.modules.user.UserService;
import vn.dtpsoft.security.JwtUtils;
import vn.dtpsoft.security.UserDetailsImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Value("${app.jwt.expirationInMs}")
    private long jwtExpirationInMs;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/login")
    public ApiMessageDto<Object> authenticateUser(@Valid @RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(userDetails);
        TokenAuthDto tokenAuthDto = new TokenAuthDto(jwt, jwtExpirationInMs);
        return makeResponse(true, tokenAuthDto,"Login successful!");
    }

    @PostMapping("/signup")
    public ApiMessageDto<Object> registerUser(@Valid @RequestBody SignupForm signUpForm) {
        if (userRepository.existsByUsername(signUpForm.getUsername())) {
            throw new BadRequestException("Username da ton tai");
        }

        if (userRepository.existsByEmail(signUpForm.getEmail())) {
            throw new BadRequestException("Email da ton tai");
        }
        // Create new user's account
        User user = new User();
        user.setUsername(signUpForm.getUsername());
        user.setEmail(signUpForm.getEmail());
        user.setPhone(signUpForm.getPhone());
        user.setFirstName(signUpForm.getFirstName());
        user.setLastName(signUpForm.getLastName());
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));

        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new BadRequestException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return makeResponse(true,user,"User registered successfully!");
    }

}
