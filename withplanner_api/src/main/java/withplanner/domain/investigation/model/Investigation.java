package withplanner.domain.investigation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withplanner.domain.user.model.User;
import withplanner.global.entity.BaseTimeEntity;
import withplanner.global.entity.Status;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Investigation extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Integer q1; //질문 1의 답변 -1,2,3,4학년 중 선택
    private Integer q2; //질문 2의 답변 - (예=1 / 아니오 = 2)
    private Integer q3; //질문 3의 답변 - (예=1 / 아니오 = 2)
    private Integer q4; //질문 4의 답변 - (예=1 / 아니오 = 2)
    private Integer q5; //질문 5의 답변 - (예=1 / 아니오 = 2)
    private Integer q6; //질문 6의 답변 - (예=1 / 아니오 = 2)
    private Integer q7; //질문 7의 답변 - (예=1 / 아니오 = 2)
    private Integer q8; //질문 8의 답변 - (예=1 / 아니오 = 2)

    @Enumerated(EnumType.STRING)
    private Status status; //Enum - ACTIVE,  INACTIVE

    private Long userId;

    @Builder
    public Investigation(Integer q1,
                         Integer q2,
                         Integer q3,
                         Integer q4,
                         Integer q5,
                         Integer q6,
                         Integer q7,
                         Integer q8) {
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q8 = q8;
        this.status = Status.ACTIVE;
    }

//    public void addUser(Long userId) {
//        this.userId = userId;
//    }
}
