package withplanner.global.dto.join;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class EmailAuthRes {
    private Boolean isSend;

    public EmailAuthRes(Boolean isSend) {
        this.isSend = isSend;
    }
}
