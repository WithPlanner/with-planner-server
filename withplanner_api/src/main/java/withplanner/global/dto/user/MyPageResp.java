package withplanner.global.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import withplanner.global.dto.post.ListCardResp;

import java.util.List;

@Getter
@Data
@NoArgsConstructor
public class MyPageResp {
    private Long userId;
    private String nickname;
    private String email;
    private List<ListCardResp> communities;

    @Builder

    public MyPageResp(Long userId, String nickname, String email, List<ListCardResp> communities) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.communities = communities;
    }
}
