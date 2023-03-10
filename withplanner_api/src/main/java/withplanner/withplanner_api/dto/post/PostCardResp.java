package withplanner.withplanner_api.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import withplanner.withplanner_api.domain.PostImg;

import java.util.List;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCardResp {
    private Long postId;
    private String name;
    private String content;
    private List<PostImg> images;
    private String writerNickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt; //인증시간

    @Builder
    public PostCardResp(Long postId, String name, String content, List<PostImg> images, String writerNickname) {
        this.postId = postId;
        this.name = name;
        this.content = content;
        this.images = images;
        this.writerNickname = writerNickname;

    }
}
