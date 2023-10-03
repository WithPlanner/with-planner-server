package withplanner.global.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import withplanner.domain.community.model.Community;

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
    private String publicType; //public인지 private인지
    private String password; //private인 경우

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private String createdAt; //습관모임 생성 날짜(요일만 보내줌)

    @Builder
    static public CommunityGetInfoRes toDto(Community community){
        return CommunityGetInfoRes.builder()
                .communityImg(community.getCommunityImg())
                .name(community.getName())
                .introduce(community.getIntroduce())
                .headCount(community.getMaxCount())
                .currentCount(community.getCurrentCount())
                .time(community.getTime())
                .days(community.getDays())
                .createdAt(community.getCreatedAt())
                .publicType(community.getIsPublic().toString())
                .password(community.getPassword())
                .build();
    }

    @Builder
    public CommunityGetInfoRes(String name, String createdAt, String introduce, String communityImg, int headCount, int currentCount, List<String> days, LocalTime time,  String type, String publicType, String password) {
        this.communityImg = communityImg;
        this.name = name;
        this.createdAt = createdAt;
        this.introduce = introduce;
        this.headCount = headCount;
        this.currentCount = currentCount;
        this.time = time;
        this.type = type;
        this.days = days;
        this.publicType = publicType;
        this.password = password;
    }
}
