package withplanner.domain.community.model;

import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.*;
import lombok.*;
import org.geolatte.geom.Geometry;
import org.locationtech.jts.geom.Point;
import withplanner.global.entity.BaseTimeEntity;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "maps")
public class Map extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alias; //별칭

    @Column(nullable = false, columnDefinition = "GEOMETRY")
    private Point coordinate; //위경도

    private String name; //상호명 (ex. 성신여자대학교 수정관)

    @OneToOne(mappedBy = "map", fetch = FetchType.LAZY)
    private CommunityMember communityMember;

    public void changeAlias(String alias){
        this.alias = alias;
    }

    @Builder
    public Map(Point coordinate, String alias, String name){
        Assert.notNull(coordinate,"좌표정보는 null값이 들어올 수 없습니다.");

        this.coordinate = coordinate;
        this.alias = alias;
        this.name = name;
    }

    //Map에 CommunityMember연결
    public void setCommunityMember(CommunityMember communityMember){
        this.communityMember = communityMember;
    }

}
