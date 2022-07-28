package withplanner.withplanner_api.dto.community;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Data
@NoArgsConstructor
public class CommunityMakeReq {
    private MultipartFile communityImg;
    private String name;
    private String introduce;
    private String category;
    private int headCount;
    private Long userIdx;
    private List<String> day;
    private String time;
    private String type;
}
