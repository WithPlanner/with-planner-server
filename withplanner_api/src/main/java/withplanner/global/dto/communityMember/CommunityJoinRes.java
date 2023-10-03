package withplanner.global.dto.communityMember;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommunityJoinRes {
    private Long communityId; //커뮤니티 id;
    private Long userId; //커뮤니티에 참여한 유저 id;
}
