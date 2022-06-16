package withplanner.withplanner_api.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Investigation extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name="investigation_idx")
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
    private Status  status; //Enum - ACTIVE,  INACTIVE

    @OneToOne(mappedBy = "investigation", fetch = FetchType.LAZY)
    private User user;

}
