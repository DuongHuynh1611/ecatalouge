package vn.dtpsoft.modules.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<Role> findByIdIn(List<Integer> ids) {
        return roleRepository.findByIdIn(ids);
    }
}
