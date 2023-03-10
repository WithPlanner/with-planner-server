package withplanner.withplanner_api.dto.post;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Data
@NoArgsConstructor
public class PostCreateReq {
    private String name;
    private String content;
    private MultipartFile img;
}
