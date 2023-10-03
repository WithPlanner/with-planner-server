package withplanner.global.dto.join;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class AuthNumberRes {
    private Boolean isValid;
    private String msg;

    public AuthNumberRes(Boolean isValid) {
        this.isValid = isValid;
    }

    public AuthNumberRes(Boolean isValid, String msg) {
        this.isValid = isValid;
        this.msg = msg;
    }
}
