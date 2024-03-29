package withplanner.withplanner_api.dto.community;

import lombok.Builder;
import lombok.Data;
import withplanner.withplanner_api.domain.Map;

@Builder
@Data
public class CommunityCreateLocationRes {
    private Long mapId; //mapId
    private double longitude ; //경도
    private double latitude ; //위도
    private String roadAddress; //도로명 주소
    private String address; //지번 주소
    private String alias; //별칭(nullable)
    private String name; //상호명

    @Builder
    static public CommunityCreateLocationRes toDto(Map map){
        return CommunityCreateLocationRes.builder()
                .mapId(map.getId())
                .longitude(map.getX())
                .latitude(map.getY())
                .roadAddress(map.getLocation().getRoadAddress())
                .address(map.getLocation().getAddress())
                .alias(map.getAlias())
                .name(map.getName())
                .build();
    }


}
