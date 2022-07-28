package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.communityMember.CommunityJoinReq;
import withplanner.withplanner_api.dto.communityMember.CommunityJoinRes;
import withplanner.withplanner_api.service.CommunityMemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityMemberController {
    private final CommunityMemberService communityMemberService;

    /**
     * 커뮤니티 참여 (jwtToken 필요)
     * @param communityId
     * @return userId, communityId
     */
    @PostMapping("/community/join/{communityId}")
    public CommunityJoinRes communityJoin(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId){
        Long userId = user.getId();
        CommunityJoinRes communityJoinRes = communityMemberService.joinCommunity(communityId,userId);
        return communityJoinRes;
    }
}
