package withplanner.withplanner_api.dto.post;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withplanner.withplanner_api.domain.PostImg;

import java.util.List;

@Getter
@Data
@NoArgsConstructor
public class PostCardResp {
    private Long postId;
    private String name;
    private String content;
    private List<PostImg> images;
    private String writerNickname;

    @Builder
    public PostCardResp(Long postId, String name, String content, List<PostImg> images, String writerNickname) {
        this.postId = postId;
        this.name = name;
        this.content = content;
        this.images = images;
        this.writerNickname = writerNickname;
    }
}
