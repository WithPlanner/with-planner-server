package withplanner.domain.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

//    private final CommunityService communityService;
//    private final MapService mapService;
//
//    /**
//     * 커뮤니티 정보 불러오기 (jwt 토큰 필요)
//     * - 참여 다이얼로그 클릭 시 불러오는 api
//     * @param communityId
//     */
//    @GetMapping(value="/community/info/{communityId}")
//    public BaseResponse<CommunityGetInfoRes> getCommunityInfo(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId) {
//
//        CommunityGetInfoRes communityGetInfoRes = communityService.getCommunityInfo(communityId);
//        return new BaseResponse<>(communityGetInfoRes);
//    }
//
//    @PostMapping(value = "/make/loc", consumes = {"multipart/form-data"})
//    public BaseResponse<CommunityCreateRes> createMapCommunity(@ModelAttribute CommunityMakeReq reqDto, @AuthenticationPrincipal User user) {
//        CommunityCreateRes result = communityService.createMapCommunity(reqDto, user.getUsername());
//        return new BaseResponse<>(result);
//    }
//
//    @PostMapping(value = "/make/post", consumes = {"multipart/form-data"})
//    public BaseResponse<CommunityCreateRes> createPostCommunity(@ModelAttribute CommunityMakeReq reqDto, @AuthenticationPrincipal User user) {
//        CommunityCreateRes result = communityService.createPostCommunity(reqDto, user.getUsername());
//        return new BaseResponse<>(result);
//    }
//
//    @GetMapping("/community/post/{communityIdx}")
//    public BaseResponse<CommunityResp> getPostCommunityMain(@PathVariable Long communityIdx) {
//        CommunityResp result = communityService.getPostCommunityMain(communityIdx);
//        return new BaseResponse<>(result);
//    }
//
//    @GetMapping("/community/map_post/{communityIdx}")
//    public BaseResponse<CommunityMapRes> getMapPostCommunityMain(@AuthenticationPrincipal User user, @PathVariable Long communityIdx) {
//        CommunityMapRes communityMapRes = communityService.getMapPostCommunityMain(communityIdx);
//        return new BaseResponse<>(communityMapRes);
//    }
//
//    @GetMapping("/main")
//    public BaseResponse<MainListResp> mainListing(@AuthenticationPrincipal User user) {
//        MainListResp result = communityService.mainListing(user);
//        return new BaseResponse<>(result);
//    }
//
//    @GetMapping("/main/search")
//    public BaseResponse<List<SearchCardResp>> searchListing(@AuthenticationPrincipal User user, @RequestParam String query) {
//        List<SearchCardResp> result = communityService.searchCommunity(user, query);
//        return new BaseResponse<>(result);
//    }
}
