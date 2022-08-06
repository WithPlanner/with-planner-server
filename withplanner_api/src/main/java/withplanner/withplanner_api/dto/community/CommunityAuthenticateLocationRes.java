package withplanner.withplanner_api.dto.community;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityAuthenticateLocationRes {
    private boolean saveStatus; //저장 상태 여부
}
