package withplanner.withplanner_api.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_idx")
    private Long id;

    private String content;
    private Integer depth;
    private Integer groupp;
    private Integer orders;
    private Integer parents;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_idx")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="map_post_idx")
    private MapPost mapPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private User user;


    public Comment(String content){
        this.content = content;
        this.status = Status.ACTIVE;
    }

    //연관 관계 편의 메소드 - mapPost
    public void connectMapPost(MapPost mapPost){
        this.mapPost = mapPost;
        mapPost.getComments().add(this);
    }
    //연관 관계 편의 메소드 - post
    public void  connectPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }
    //연관 관계 편의 메소드 - user
    public void connectUser(User user){
        this.user = user;
        user.getComments().add(this);
    }


}
