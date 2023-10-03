package withplanner.global.dto.post;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class SearchCardResp {
    private Long communityId;
    private String name;
    private String communityImg;
    private String type;
    private String category;
    private String publicType; //공개, 비공개 여부

    @Builder
    public SearchCardResp(Long communityId, String name, String communityImg, String type, String category, String publicType) {
        this.communityId = communityId;
        this.name = name;
        this.communityImg = communityImg;
        this.type = type;
        this.category = category;
        this.publicType = publicType;
    }

}
