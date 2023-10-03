package withplanner.domain.post.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withplanner.global.entity.BaseTimeEntity;
import withplanner.global.entity.Status;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean isAuthenticate;

    private String addressAlias;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    private Long userId;

    private Long communityId;

    //todo : 논의할 부분 : 양방향임
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    //todo : 논의할 부분 : 양방향임
    @OneToMany(mappedBy = "post")
    private List<PostImage> images = new ArrayList<>();

    @Builder
    public Post(String name, String content) {
        this.name = name;
        this.content = content;
        this.status = Status.ACTIVE;
    }

//    public void addUser(User user) {
//        this.user = user;
//    }

//    public void addCommunity(Community community) {
//        this.community = community;
//    }

    public void addPostImg(PostImage postImage) {
        images.add(postImage);
        postImage.addPost(this);
    }
}
