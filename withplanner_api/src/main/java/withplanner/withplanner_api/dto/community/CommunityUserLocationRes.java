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
    private String address; //default는 도로명 주소, 도로명주소가 없는 데이터의 경우 지번 주소
    private double longitude; //경도
    private double latitude; //위도

    public CommunityUserLocationRes(String nickname, double longitude, double latitude,String alias, String address){
        this.nickname = nickname;
        this.longitude = longitude;
        this.latitude = latitude;
        this.alias = alias;
        this.address = address;
    }
}
