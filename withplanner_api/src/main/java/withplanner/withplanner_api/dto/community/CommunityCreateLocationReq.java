package withplanner.withplanner_api.dto.community;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCreateLocationReq {
    private double longitude ; //경도
    private double latitude ; //위도
    private String roadAddress; //도로명 주소
    private String address; //지번 주소
    private String alias; //별칭(nullable)
    private String name; //상호명
}
