package withplanner.withplanner_api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostImg extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name="photo_idx")
    private Long id;

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx")
    @JsonIgnore
    private Post post;

    public void addPost(Post post) {
        this.post = post;
    }

    public PostImg(String imgUrl) {
        this.imgUrl = imgUrl;
        this.status = Status.ACTIVE;
    }
}
