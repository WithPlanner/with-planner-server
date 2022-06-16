package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CommunityMember extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name="community_member_idx")
    private Long id;

    //fk userIdx
    //fk communityIdx

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean authority; // true(방장)&false(멤버)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_idx")
    private Community community;

    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name= "map_idx")
    private Map map;




}
