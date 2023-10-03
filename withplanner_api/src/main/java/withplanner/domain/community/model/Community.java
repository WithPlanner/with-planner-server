package withplanner.domain.community.model;

import jakarta.persistence.*;
import lombok.*;
import withplanner.global.entity.BaseTimeEntity;
import withplanner.domain.post.model.Post;
import withplanner.global.entity.Status;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "communities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String introduce;

    private String communityImg;

    @OneToOne(fetch = FetchType.LAZY)
    private Category category;

    private Integer maxCount; //최대인원

    private Integer currentCount; //현재인원

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection
    private List<String> days = new ArrayList<>();

    private LocalTime time; //인증시간

    @Enumerated(EnumType.STRING)
    private CommunityProgressStatus communityProgressStatus;

    private Boolean isLocationAuthenticationRequired;

//    //todo : 논의할 부분 : 양방향임
//    @OneToMany(mappedBy = "community")
//    private List<CommunityMember> communityMembers= new ArrayList<>();

    private LocalDateTime finishDate; //커뮤니티 인증 종료 날짜

    private Boolean isPublic; //공개 비공개 여부

    private String password; //비공개 시 비밀번호

    //커뮤니티 가입 시 currentCount +1
    public void plusCurrentCount() {
        this.currentCount += 1;
    }

    @Builder
    public Community(String name,
                     String introduce,
                     String communityImg,
                     Category category,
                     Integer maxCount,
                     Integer currentCount,
                     Long userId,
                     Status status,
                     List<String> days,
                     LocalTime time,
                     CommunityProgressStatus communityProgressStatus,
                     Boolean isLocationAuthenticationRequired,
                     List<CommunityMember> communityMembers,
                     LocalDateTime finishDate,
                     Boolean isPublic,
                     String password) {
        this.name = name;
        this.introduce = introduce;
        this.communityImg = communityImg;
        this.category = category;
        this.maxCount = maxCount;
        this.currentCount = currentCount;
        this.userId = userId;
        this.status = status;
        this.days = days;
        this.time = time;
        this.communityProgressStatus = communityProgressStatus;
        this.isLocationAuthenticationRequired = isLocationAuthenticationRequired;
//        this.posts = posts;
//        this.communityMembers = communityMembers;
        this.finishDate = finishDate;
        this.isPublic = isPublic;
        this.password = password;
    }

    public void addPost(Post post) {
        //posts.add(post);
        //post.addCommunity(this);
    }

//    public void addCommunityMember(CommunityMember communityMember) {
//        communityMembers.add(communityMember);
//    }

}
