package withplanner.global.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import withplanner.domain.post.model.PostImage;

import java.util.List;

@Builder
@Getter
@Setter
public class CommunityPostDetailRes {

    private Long postId;
    private String name;
    private String content;
    private List<PostImage> images;
    private String writerNickname;
    private List<CommunityCommentDto> comments;
    private Boolean authorStatus; //로그인 한 사람(요청을 보낸 사람)과 글을 작성한 사람이 같은지 나타내는 boolean

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt;//글 작성 시간
}
