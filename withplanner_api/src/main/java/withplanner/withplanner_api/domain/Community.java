package withplanner.withplanner_api.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Community extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name="community_idx")
    private Long id;
    private String name;
    private String introduce;
    private String communityImg;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer headCount; //최대인원

    private Integer currentCount; //현재인원

    @ElementCollection
    private List<String> days = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalTime time; //인증시간

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "community")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "community")
    private List<MapPost> mapPosts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User createUser; //개설한 회원

    @OneToMany(mappedBy = "community")
    private List<CommunityMember> communityMembers= new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PublicType publicType; //공개 비공개 여부

    private String password; //비공개 시 비밀번호


    //커뮤니티 가입 시 currentCount +1
    public void plusCurrentCount() {
        this.currentCount += 1;
    }

    @Builder
    public Community(String name, String introduce, String communityImg, Integer headCount, Category category, List<String> days, LocalTime time, Type type, PublicType publicType, String password) {
        this.name = name;
        this.introduce = introduce;
        this.communityImg = communityImg;
        this.headCount = headCount;
        this.category = category;
        this.days = days;
        this.time = time;
        this.type = type;
        this.status = Status.ACTIVE;
        this.currentCount = 1;
        this.publicType = publicType;
        this.password = password;
    }

    public void addUser(User user) {
        this.createUser = user;
    }

    public void addPost(Post post) {
        posts.add(post);
        post.addCommunity(this);
    }

    public void addCommunityMember(CommunityMember communityMember) {
        communityMembers.add(communityMember);
    }
}
