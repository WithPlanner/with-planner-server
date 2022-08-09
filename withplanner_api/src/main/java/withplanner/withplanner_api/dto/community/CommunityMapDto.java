package withplanner.withplanner_api.dto.community;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommunityMapDto {
    private Long mapPostId; //mapPost id
    private Long userId; //글 쓴 유저 id
    private String nickName; //닉네임
    private String profileImg; //프로필 이미지
    private String location; //1순위: 별칭, 2순위 : 장소명, 3순위: 도로명주소, 4순위: 지번주소
    private LocalDateTime updatedAt; //인증시간
}
