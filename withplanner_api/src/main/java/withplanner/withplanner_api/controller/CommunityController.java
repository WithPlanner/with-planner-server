package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.JsonResult;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;
import withplanner.withplanner_api.dto.community.CommunityResp;
import withplanner.withplanner_api.dto.post.ListCardResp;
import withplanner.withplanner_api.dto.post.MainListResp;
import withplanner.withplanner_api.service.CommunityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping(value = "/make/loc", consumes = {"multipart/form-data"})
    public Long createMapCommunity(@ModelAttribute CommunityMakeReq reqDto, @AuthenticationPrincipal User user) {
        return communityService.createMapCommunity(reqDto, user.getUsername());
    }

    @PostMapping(value = "/make/post", consumes = {"multipart/form-data"})
    public Long createPostCommunity(@ModelAttribute CommunityMakeReq reqDto, @AuthenticationPrincipal User user) {
        return communityService.createPostCommunity(reqDto, user.getUsername());
    }

    @GetMapping("/community/post/{communityIdx}")
    public CommunityResp getPostCommunityMain(@PathVariable Long communityIdx) {
        return communityService.getPostCommunityMain(communityIdx);
    }

    @GetMapping("/main")
    public MainListResp mainListing(@AuthenticationPrincipal User user) {
        return communityService.mainListing(user);
    }

    @GetMapping("/main/search")
    public JsonResult searchListing(@RequestParam String query) {
        List<ListCardResp> communities = communityService.searchCommunity(query);
        return new JsonResult(communities.size(), communities);
    }
}
