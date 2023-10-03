package withplanner.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.domain.user.model.User;
import withplanner.global.dto.community.CommunityPostDetailRes;
import withplanner.global.dto.post.PostCardResp;
import withplanner.global.dto.post.PostCreateReq;
import withplanner.global.exception.BaseResponse;
import withplanner.domain.post.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/community/post/write/{communityIdx}", consumes = {"multipart/form-data"})
    public BaseResponse<PostCardResp> createPost(@PathVariable Long communityIdx, @ModelAttribute PostCreateReq reqDto, @AuthenticationPrincipal User user) {
        PostCardResp result = postService.createPost(reqDto, communityIdx, user.getUsername());
        return new BaseResponse<>(result);
    }

    @GetMapping("/community/post/all/{communityIdx}")
    public BaseResponse<List<PostCardResp>> getAllPost(@PathVariable Long communityIdx) {
        List<PostCardResp> result = postService.getAllPost(communityIdx);
        return new BaseResponse<>(result);
    }

    //게시물 상세 조회
    @GetMapping("/community/post/detail/{postIdx}")
    public BaseResponse<CommunityPostDetailRes> getDetailPost(@PathVariable Long postIdx, @AuthenticationPrincipal User user){
        Long userId = user.getId();

        CommunityPostDetailRes communityPostDetailRes = postService.getDetailPost(userId,postIdx);
        return new BaseResponse<>(communityPostDetailRes);
    }
}
