package withplanner.withplanner_api.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.community.*;
import withplanner.withplanner_api.dto.post.PostCardResp;
import withplanner.withplanner_api.exception.BaseResponse;
import withplanner.withplanner_api.service.CommunityService;
import withplanner.withplanner_api.service.MapService;

import java.util.List;

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
    public BaseResponse<CommunityCreateLocationRes> createLocation(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId, @RequestBody CommunityCreateLocationReq reqDto){
        Long userId = user.getId();

        CommunityCreateLocationRes communityCreateLocationRes = mapService.createLocation(reqDto, userId, communityId);
        return new BaseResponse<>(communityCreateLocationRes);
    }

    /**
     * 위치 인증 (jwt 토큰 필요)
     * @param communityId
     */
    @PostMapping(value="/community/loc/authenticate/{communityId}")
    public BaseResponse<CommunityAuthenticateLocationRes> authenticateLocation(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId, @RequestBody CommunityAuthenticateLocationReq reqDto){
        Long userId = user.getId();

        CommunityAuthenticateLocationRes communityAuthenticateLocationRes = mapService.authenticateLocation(userId,communityId,reqDto);
        return new BaseResponse<>(communityAuthenticateLocationRes);
    }

    /**
     * 회원 위치 정보 가져오기 (jwt 토큰 필요)
     * - 위치 인증 dialog에서 불러오는 api
     * @param communityId
     */
    @GetMapping(value="/community/loc/user_location/{communityId}")
    public BaseResponse<CommunityUserLocationRes> getUserLocation(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId){
        Long userId = user.getId();

        CommunityUserLocationRes communityUserLocationRes = mapService.getUserLocation(userId, communityId);
        return new BaseResponse<>(communityUserLocationRes);
    }

    /**
     * mapPost 게시물 전체 가져오기
     * @param communityIdx
     */
    @GetMapping("/community/map-post/all/{communityIdx}")
    public BaseResponse<List<CommunityMapDto>> getAllMapPost(@PathVariable Long communityIdx) {
        List<CommunityMapDto> result = mapService.getAllMapPost(communityIdx);
        return new BaseResponse<>(result);
    }

    /**
     * mapPost 상세조회(+댓글)
     * @param mapPostIdx
     */
    @GetMapping("/community/map-post/detail/{mapPostIdx}")
    public BaseResponse<CommunityMapPostDetailRes> getDetailMapPost(@PathVariable Long mapPostIdx, @AuthenticationPrincipal User user){
        Long userId = user.getId();

        CommunityMapPostDetailRes communityMapPostDetailRes = mapService.getDetailMapPost(userId,mapPostIdx);
        return new BaseResponse<>(communityMapPostDetailRes);
    }

}
