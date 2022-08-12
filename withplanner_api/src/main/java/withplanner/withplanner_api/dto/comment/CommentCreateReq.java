package withplanner.withplanner_api.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.dto.community.CommunityGetInfoRes;

import java.time.LocalDateTime;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateReq {
    private String comment;


}
