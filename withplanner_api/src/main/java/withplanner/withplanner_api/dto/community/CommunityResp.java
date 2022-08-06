package withplanner.withplanner_api.dto.community;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withplanner.withplanner_api.dto.post.PostCardResp;

import java.util.List;

@Getter
@Data
@NoArgsConstructor
public class CommunityResp {
    private Long communityId;
    private String name;
    private String createdAt;
    private String introduce;
    private String communityImg;
    private String category;
    private int headCount;
    private int currentCount;
    private List<PostCardResp> posts;

    @Builder
    public CommunityResp(Long communityId, String name, String createdAt, String introduce, String communityImg, String category, int headCount, int currentCount, List<PostCardResp> posts) {
        this.communityId = communityId;
        this.name = name;
        this.createdAt = createdAt;
        this.introduce = introduce;
        this.communityImg = communityImg;
        this.category = category;
        this.headCount = headCount;
        this.currentCount = currentCount;
        this.posts = posts;
    }
}
