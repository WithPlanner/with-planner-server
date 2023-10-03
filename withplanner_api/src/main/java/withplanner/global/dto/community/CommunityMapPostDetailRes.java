package withplanner.global.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Builder
@Getter
@Setter
public class CommunityMapPostDetailRes {
    private Long mapPostId; //mapPost id
    private Long userId; //글 쓴 유저 id
    private String nickName; //닉네임
    private String location; //1순위: 별칭, 2순위 : 장소명, 3순위: 도로명주소, 4순위: 지번주소
    private List<CommunityCommentDto> comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt; //인증시간
}
