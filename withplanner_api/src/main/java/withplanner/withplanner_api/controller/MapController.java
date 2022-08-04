package withplanner.withplanner_api.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationReq;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationRes;
import withplanner.withplanner_api.dto.community.CommunityUserLocationRes;
import withplanner.withplanner_api.service.CommunityService;
import withplanner.withplanner_api.service.MapService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MapController {
    private final CommunityService communityService;
    private final MapService mapService;

    /**
     * 위치 및 별칭 등록 (jwt 토큰 필요)
     * @param communityId
     */
    @PostMapping(value = "/community/loc/search/{communityId}")
    public CommunityCreateLocationRes createLocation(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId, @ModelAttribute CommunityCreateLocationReq reqDto){
        Long userId = user.getId();

        CommunityCreateLocationRes communityCreateLocationRes = mapService.createLocation(reqDto, userId, communityId);
        return communityCreateLocationRes;
    }

    /**
     * 위치 인증 (jwt 토큰 필요)
     * @param communityId
     */
//    @PostMapping(value="/community/loc/authenticate/{communityId}")
//    public CommunityAuthenticateLocationRes authenticateLocation(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId, @ModelAttribute CommunityAuthenticateLocationReq reqDto){
//        Long userId = user.getId();

//        CommunityAuthenticateLocationRes communityAuthenticateLocationRes = mapService.authenticateLocation(reqDto,userId,communityId);
//        return communityAuthenticateLocationRes;
//    }



}
