package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationReq;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationRes;
import withplanner.withplanner_api.dto.community.CommunityGetInfoRes;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.exception.BaseResponse;
import withplanner.withplanner_api.service.CommunityService;
import withplanner.withplanner_api.service.MapService;

import static withplanner.withplanner_api.exception.BaseResponseStatus.EXPIRED_JWT_TOKEN;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final MapService mapService;

/*    @PostMapping(value = "/make/loc", consumes = {"multipart/form-data"})
    public Long createMapCommunity(@RequestBody CommunityMakeReq reqDto) {
        return communityService.createMapCoummunity(reqDto);
    }*/

    /**
     * 커뮤니티 정보 불러오기 (jwt 토큰 필요)
     * - 참여 다이얼로그 클릭 시 불러오는 api
     * @param communityId
     */
    @GetMapping(value="/community/info/{communityId}")
    public BaseResponse<CommunityGetInfoRes> getCommunityInfo(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId){

        CommunityGetInfoRes communityGetInfoRes = communityService.getCommunityInfo(communityId);
        return new BaseResponse<>(communityGetInfoRes);
    }



}
