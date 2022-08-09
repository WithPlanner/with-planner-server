package withplanner.withplanner_api.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String createdAt;//커뮤니티 생성 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt;// 커뮤니티 수정 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalTime time; //인증하기로 한 시간
    private List<String> days; //인증하기로 한 요일
    private List<CommunityMapDto> mapPosts;
}
