package vn.dtpsoft.modules.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.dtpsoft.constant.ERole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
    Optional<Role> findById(Long id);
    List<Role> findByIdIn(List<Integer> ids);
}
