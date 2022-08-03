package withplanner.withplanner_api.dto.community;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class CommunityCreateLocationReq {
    private double longitude ; //경도
    private double latitude ; //위도
    private String zipcode; //우편번호
    private String roadAddress; //도로명 주소
    private String address; //지번 주소
    private String alias; //별칭(nullable)

}
