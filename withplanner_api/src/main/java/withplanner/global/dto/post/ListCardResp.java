package withplanner.global.dto.post;

import lombok.*;

@Getter
@Data
@NoArgsConstructor
public class ListCardResp {
    private Long communityId;
    private String name;
    private String communityImg;
    private String type;
    private String category;

    @Builder
    public ListCardResp(Long communityId, String name, String communityImg, String type, String category) {
        this.communityId = communityId;
        this.name = name;
        this.communityImg = communityImg;
        this.type = type;
        this.category = category;
    }
}
