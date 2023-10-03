package withplanner.domain.community.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import withplanner.global.entity.BaseTimeEntity;
import withplanner.global.entity.Status;


@Entity
@Getter
@Table(name = "community_members")
public class CommunityMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean isMaster; // true(방장)&false(멤버)

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name= "map_id")
    private Map map;

    public void connectCommunityMember(Boolean authority, Status status, Community community, Long userId){
        this.isMaster = authority;
        this.status  = status;
        this.community = community;
        this.userId = userId;
        community.addCommunityMember(this);
    }

    //연관관계 메서드
    public void connectMap(Map map){
        this.map = map;
        map.setCommunityMember(this);
    }

}
