package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name="comment_idx")
    private Long id;
    private String content;
    private Integer depth;
    private Integer group;
    private Integer order;
    private Integer parent;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_idx")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private User user;

}
