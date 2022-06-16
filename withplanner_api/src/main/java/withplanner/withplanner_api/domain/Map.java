package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Map extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name="map_idx")
    private Long id;

    private String x ; //경도
    private String y ; //위도

    private Address address; //주소

    private String alias;

    @OneToOne(mappedBy = "map", fetch = FetchType.LAZY)
    private CommunityMember communityMember;
}
