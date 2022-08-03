package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationReq;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationRes;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;
import withplanner.withplanner_api.service.CommunityService;
import withplanner.withplanner_api.service.MapService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final MapService mapService;

    @PostMapping(value = "/make/loc", consumes = {"multipart/form-data"})
    public Long createMapCommunity(@ModelAttribute CommunityMakeReq reqDto) {
        return communityService.createMapCoummunity(reqDto);
    }

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
}
