package withplanner.domain.post.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import withplanner.global.entity.BaseTimeEntity;
import withplanner.global.entity.Status;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Integer depth;

    private Integer grp;

    private Integer ord;

    private Integer parent;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    private Long userId;

    public Comment(String content){
        this.content = content;
        this.status = Status.ACTIVE;
    }

    //연관 관계 편의 메소드 - post
    public void  connectPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }
    //연관 관계 편의 메소드 - user
    public void connectUser(Long userId) {
        this.userId = userId;
    }
}
