package withplanner.withplanner_api.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.domain.Map;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityGetInfoRes {

    private String communityImg; //습관모임 대표사진
    private String name; //습관모임 이름
    private String introduce; //습관모임 소개
    private int headCount; //최대 인원
    private int currentCount; //현재 인원

    private String type; //mapPost, post 타입
    private LocalTime time; //인증하기로 한 시간
    private List<String> days; //인증하기로 한 요일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private String createdAt; //습관모임 생성 날짜(요일만 보내줌)

    @Builder
    static public CommunityGetInfoRes toDto(Community community){
        return CommunityGetInfoRes.builder()
                .communityImg(community.getCommunityImg())
                .name(community.getName())
                .introduce(community.getIntroduce())
                .headCount(community.getHeadCount())
                .currentCount(community.getCurrentCount())
                .type(community.getType().toString())
                .time(community.getTime())
                .days(community.getDays())
                .createdAt(community.getCreatedAt())
                .build();
    }
}
