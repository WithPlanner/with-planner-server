package withplanner.withplanner_api.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withplanner.withplanner_api.dto.post.PostCardResp;

import java.time.LocalTime;
import java.util.List;

@Getter
@Data
@NoArgsConstructor
public class CommunityResp {
    private Long communityId;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String createdAt;
    private String updatedAt;
    private String introduce;
    private String communityImg;
    private String category;
    private int headCount;
    private int currentCount;
    private List<String> days;
    private String time;
    private String type;
    private List<PostCardResp> posts;

    @Builder
    public CommunityResp(Long communityId, String name, String createdAt, String introduce, String communityImg, String category, int headCount, int currentCount, List<String> days, String time, List<PostCardResp> posts, String type, String updatedAt) {
        this.communityId = communityId;
        this.name = name;
        this.createdAt = createdAt;
        this.introduce = introduce;
        this.communityImg = communityImg;
        this.category = category;
        this.headCount = headCount;
        this.currentCount = currentCount;
        this.time = time;
        this.days = days;
        this.posts = posts;
        this.type = type;
        this.updatedAt = updatedAt;
    }
}
