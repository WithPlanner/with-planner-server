package withplanner.global.dto.investigation;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class InvestReq {
    private Long userIdx;
    private int q1;
    private int q2;
    private int q3;
    private int q4;
    private int q5;
    private int q6;
    private int q7;
    private int q8;
}
