package vn.dtpsoft.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.dtpsoft.modules.user.User;
import vn.dtpsoft.modules.user.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetailsImpl loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(usernameOrEmail)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username/email: " + usernameOrEmail));

    return UserDetailsImpl.build(user);
  }

}
