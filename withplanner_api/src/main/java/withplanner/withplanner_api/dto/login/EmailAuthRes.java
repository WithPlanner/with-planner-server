package withplanner.withplanner_api.dto.login;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class EmailAuthRes {
    Boolean isSend;

    public EmailAuthRes(Boolean isSend) {
        this.isSend = isSend;
    }
}
