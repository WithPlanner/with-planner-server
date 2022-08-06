package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.ResultLongResp;
import withplanner.withplanner_api.dto.post.MainListResp;
import withplanner.withplanner_api.dto.post.PostCreateReq;
import withplanner.withplanner_api.exception.BaseResponse;
import withplanner.withplanner_api.service.PostService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/community/post/write/{communityIdx}", consumes = {"multipart/form-data"})
    public BaseResponse<ResultLongResp> createPost(@PathVariable Long communityIdx, @ModelAttribute PostCreateReq reqDto, @AuthenticationPrincipal User user) {
        ResultLongResp result = postService.createPost(reqDto, communityIdx, user.getUsername());
        return new BaseResponse<>(result);
    }
}
