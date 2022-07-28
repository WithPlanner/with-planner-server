package withplanner.withplanner_api.dto.community;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class CommunityMakeReq {
    private String communityImg;
    private String name;
    private String introduce;
    private String category;
    private int headCount;
    private Long userIdx;
}
