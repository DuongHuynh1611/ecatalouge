package vn.dtpsoft.modules.role;
import lombok.Getter;
import lombok.Setter;
import vn.dtpsoft.constant.ERole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
