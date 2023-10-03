package withplanner.domain.community.model;

import jakarta.persistence.*;
import lombok.*;
import withplanner.global.entity.BaseTimeEntity;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "maps")
public class Map extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Coordinate coordinate;

    private String alias; //별칭

    private String name; //상호명 (ex. 성신여자대학교 수정관)

    @OneToOne(mappedBy = "map", fetch = FetchType.LAZY)
    private CommunityMember communityMember;

    public void changeAlias(String alias){
        this.alias = alias;
    }

    @Builder
    public Map(Coordinate coordinate, String alias, String name){
        this.coordinate = coordinate;
        this.alias = alias;
        this.name = name;
    }

    //Map에 CommunityMember연결
    public void setCommunityMember(CommunityMember communityMember){
        this.communityMember = communityMember;
    }

}
