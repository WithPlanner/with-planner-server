package withplanner.withplanner_api.domain;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Map extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name="map_idx")
    private Long id;

    private double x ; //경도
    private double y ; //위도

    private Address address; //주소

    private String alias;

    @OneToOne(mappedBy = "map", fetch = FetchType.LAZY)
    private CommunityMember communityMember;

    public void addAlias(String alias){
        this.alias = alias;
    }

    @Builder
    public Map(double x, double y, Address address, String alias){
        this.x=x;
        this.y=y;
        this.address = address;
        this.alias = alias;
    }

    //Map에 CommunityMember연결
    public void setCommunityMember(CommunityMember communityMember){
        this.communityMember = communityMember;
    }


}
