package withplanner.domain.post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withplanner.global.entity.BaseTimeEntity;
import withplanner.global.entity.Status;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_images")
public class PostImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    public void addPost(Post post) {
        this.post = post;
    }

    public PostImage(String imgUrl) {
        this.imgUrl = imgUrl;
        this.status = Status.ACTIVE;
    }
}
