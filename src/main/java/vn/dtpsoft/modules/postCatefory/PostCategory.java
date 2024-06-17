package vn.dtpsoft.modules.postCatefory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.dtpsoft.modules.post.Post;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "post_category")
@Setter
@Getter
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "postCategory")
    @JsonIgnoreProperties(value = {"postCategory","hibernateLazyInitializer"})
    private List<Post> posts;

}
