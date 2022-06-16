package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Community extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name="community_idx")
    private Long id;

    //createdAt
    //updatedAt


    private String name;
    private String introduce;
    private String communityImg;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer headCount;

    @ElementCollection
    private List<String> days =new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    private String time; //인증시간

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


}
