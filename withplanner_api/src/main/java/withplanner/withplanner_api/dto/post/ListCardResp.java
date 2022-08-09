package withplanner.withplanner_api.dto.post;

import lombok.*;

@Getter
@Data
@NoArgsConstructor
public class ListCardResp {
    private Long communityId;
    private String name;
    private String communityImg;
    private String type;

    @Builder
    public ListCardResp(Long communityId, String name, String communityImg, String type) {
        this.communityId = communityId;
        this.name = name;
        this.communityImg = communityImg;
        this.type = type;
    }
}
