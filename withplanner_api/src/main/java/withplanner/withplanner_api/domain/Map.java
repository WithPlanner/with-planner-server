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

    private String alias; //별칭

    private String name; //상호명 (ex. 성신여자대학교 수정관)

    @OneToOne(mappedBy = "map", fetch = FetchType.LAZY)
    private CommunityMember communityMember;

    public void addAlias(String alias){
        this.alias = alias;
    }

    @Builder
    public Map(double x, double y, Address address, String alias,String name){
        this.x=x;
        this.y=y;
        this.address = address;
        this.alias = alias;
        this.name = name;
    }

    //Map에 CommunityMember연결
    public void setCommunityMember(CommunityMember communityMember){
        this.communityMember = communityMember;
    }


}
