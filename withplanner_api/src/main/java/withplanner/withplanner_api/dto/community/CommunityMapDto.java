package withplanner.withplanner_api.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import withplanner.withplanner_api.domain.PostImg;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CommunityMapDto {
    private Long mapPostId; //mapPost id
    private Long userId; //글 쓴 유저 id
    private String nickName; //닉네임
    private String profileImg; //프로필 이미지
    private String location; //1순위: 별칭, 2순위 : 장소명, 3순위: 도로명주소, 4순위: 지번주소

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt; //인증시간
}
