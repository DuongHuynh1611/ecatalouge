package vn.dtpsoft.modules.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.dtpsoft.modules.postCatefory.PostCategory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "post")
@Setter
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content",columnDefinition = "text")
    private String content;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    @JsonIgnoreProperties(value = {"posts","hibernateLazyInitializer"})
    private PostCategory postCategory;
}
