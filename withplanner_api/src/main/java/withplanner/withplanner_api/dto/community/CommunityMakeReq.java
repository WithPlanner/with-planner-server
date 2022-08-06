package withplanner.withplanner_api.dto.community;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import withplanner.withplanner_api.domain.Category;

import java.time.LocalTime;
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
    private List<String> day;
    @DateTimeFormat(pattern = "kk:mm:ss")
    private LocalTime time;

    public Category getCategory() {
        switch (category) {
            case "미라클 모닝" :
                return Category.miracleMorning;
            case "관계 형성(커뮤니케이션)" :
                return Category.communication;

            case "디지털 디톡스":
                return Category.digitalDetox;

            case "멘탈관리":
                return Category.mentalCare;

            case "운동":
                return Category.exercise;

            case "홈 트레이닝" :
                return Category.homeTraining;

            case "스트래칭" :
                return Category.stretching;

            case "시간 관리" :
                return Category.timeManagement;

            case "취미 생활" :
                return Category.hobby;

            case "다이어트" :
                return Category.diet;

            case "외국어 공부" :
                return Category.foreignLanguage;

            case "글쓰기 연습 및 필사" :
                return Category.writing;

            case "나이트 루틴" :
                return Category.nightRoutine;

            case "집 정돈" :
                return Category.houseClean;

            case "취업 준비" :
                return Category.prepareEmployment;

            case "건강 습관 형성" :
                return Category.healthHabit;

            case "독서" :
                return Category.read;

            default :
                return null;
        }

    }
}
