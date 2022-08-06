package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.community.CommunityGetInfoRes;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;
import withplanner.withplanner_api.exception.BaseResponse;
import withplanner.withplanner_api.dto.ResultLongResp;
import withplanner.withplanner_api.dto.community.CommunityResp;
import withplanner.withplanner_api.dto.post.ListCardResp;
import withplanner.withplanner_api.dto.post.MainListResp;
import withplanner.withplanner_api.service.CommunityService;
import withplanner.withplanner_api.service.MapService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;
    private final MapService mapService;

    /**
     * 커뮤니티 정보 불러오기 (jwt 토큰 필요)
     * - 참여 다이얼로그 클릭 시 불러오는 api
     * @param communityId
     */
    @GetMapping(value="/community/info/{communityId}")
    public BaseResponse<CommunityGetInfoRes> getCommunityInfo(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId) {

        CommunityGetInfoRes communityGetInfoRes = communityService.getCommunityInfo(communityId);
        return new BaseResponse<>(communityGetInfoRes);
    }

    @PostMapping(value = "/make/loc", consumes = {"multipart/form-data"})
    public BaseResponse<ResultLongResp> createMapCommunity(@ModelAttribute CommunityMakeReq reqDto, @AuthenticationPrincipal User user) {
        ResultLongResp result = communityService.createMapCommunity(reqDto, user.getUsername());
        return new BaseResponse<>(result);
    }

    @PostMapping(value = "/make/post", consumes = {"multipart/form-data"})
    public BaseResponse<ResultLongResp> createPostCommunity(@ModelAttribute CommunityMakeReq reqDto, @AuthenticationPrincipal User user) {
        ResultLongResp result = communityService.createPostCommunity(reqDto, user.getUsername());
        return new BaseResponse<>(result);
    }

    @GetMapping("/community/post/{communityIdx}")
    public BaseResponse<CommunityResp> getPostCommunityMain(@PathVariable Long communityIdx) {
        CommunityResp result = communityService.getPostCommunityMain(communityIdx);
        return new BaseResponse<>(result);
    }

    @GetMapping("/main")
    public BaseResponse<MainListResp> mainListing(@AuthenticationPrincipal User user) {
        MainListResp result = communityService.mainListing(user);
        return new BaseResponse<>(result);
    }

    @GetMapping("/main/search")
    public BaseResponse<List<ListCardResp>> searchListing(@RequestParam String query) {
        List<ListCardResp> result = communityService.searchCommunity(query);
        return new BaseResponse<>(result);
    }
}
