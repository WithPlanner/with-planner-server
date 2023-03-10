package withplanner.withplanner_api.dto.community;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCreateRes {
    private Long id;
    private String msg;
    private String publicType;
}
