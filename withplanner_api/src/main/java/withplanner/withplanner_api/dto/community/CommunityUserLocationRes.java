package withplanner.withplanner_api.dto.community;

import lombok.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityUserLocationRes {
    private String nickname; //유저 닉네임
    private String alias ; //목적지 별칭
    private String roadAddress; //도로명 주소
    private String address; //지번 주소
    private double longitude; //경도
    private double latitude; //위도
}
