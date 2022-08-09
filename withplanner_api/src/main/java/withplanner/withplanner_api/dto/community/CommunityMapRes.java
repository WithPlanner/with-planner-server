package withplanner.withplanner_api.dto.community;

import lombok.*;
import withplanner.withplanner_api.domain.Day;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityMapRes {
    private Long communityId;
    private String name;
    private String introduce;
    private String communityImg;
    private String category;
    private int headCount;
    private int currentCount;
    private LocalDateTime createdAt;//커뮤니티 생성 날짜
    private LocalDateTime updatedAt;// 커뮤니티 수정 날짜
    private LocalTime time; //인증하기로 한 시간
    private List<String> days; //인증하기로 한 요일
    private List<CommunityMapDto> mapPosts;
}
