package withplanner.withplanner_api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name="post_idx")
    private Long id;

    private String name;

    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostImg> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_idx")
    private Community community;

    @Builder
    public Post(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public void addUser(User user) {
        this.user = user;
    }

    public void addCommunity(Community community) {
        this.community = community;
    }

    public void addPostImg(PostImg postImg) {
        images.add(postImg);
        postImg.addPost(this);
    }
}
