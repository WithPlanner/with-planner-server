package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.dto.communityMember.CommunityJoinReq;
import withplanner.withplanner_api.dto.communityMember.CommunityJoinRes;
import withplanner.withplanner_api.service.CommunityMemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityMemberController {
    private final CommunityMemberService communityMemberService;

    //@PostMapping("/community/join/{communityId}")
    //public CommunityJoinRes communityJoin(@RequestBody CommunityJoinReq communityJoinReq, @PathVariable("communityId") Long communityId){
    //    CommunityJoinRes communityJoinRes = communityMemberService.;
    //    return communityJoinRes;
    //}
}
