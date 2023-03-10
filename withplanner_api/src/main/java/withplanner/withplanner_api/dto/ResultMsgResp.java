package withplanner.withplanner_api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMsgResp {
    private String msg;
    private boolean result;
}
