package withplanner.withplanner_api.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityAuthenticateLocationReq {

    private Boolean authenticationStatus ; //인증 가능 여부 - 프론트에서 계산해서 올라옴

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime localDateTime; //인증 버튼 누른 시각
}
