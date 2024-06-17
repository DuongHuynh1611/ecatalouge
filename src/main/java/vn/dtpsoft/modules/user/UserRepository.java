package vn.dtpsoft.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    public Boolean existsByPhone(String phone);
    public Boolean existsByEmail(String email);
    public Boolean existsByUsername(String username);

    User getUserByUsername(String username);
}
