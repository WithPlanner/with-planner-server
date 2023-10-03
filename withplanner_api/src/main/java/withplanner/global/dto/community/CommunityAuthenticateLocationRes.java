package withplanner.global.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityAuthenticateLocationRes {
    private Long mapPostId; //mapPost id
    private Long userId; //글 쓴 유저 id
    private String nickName; //닉네임
    private String profileImg; //프로필 이미지
    private String location; //1순위: 별칭, 2순위 : 장소명, 3순위: 도로명주소, 4순위: 지번주소

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt; //인증시간
}
