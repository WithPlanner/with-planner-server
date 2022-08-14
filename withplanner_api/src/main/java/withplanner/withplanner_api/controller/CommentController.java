package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.comment.CommentCreateReq;
import withplanner.withplanner_api.dto.comment.CommentCreateRes;
import withplanner.withplanner_api.exception.BaseResponse;
import withplanner.withplanner_api.service.CommentService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성 (jwtToken 필요)
     * @param communityId
     */
    @PostMapping(value = "/comment/{communityId}/{postId}")
    public BaseResponse<CommentCreateRes> createComment(@AuthenticationPrincipal User user, @PathVariable("communityId") Long communityId,@PathVariable("postId") Long postId, @RequestBody CommentCreateReq reqDto){
        Long userId = user.getId();

        CommentCreateRes commentCreateRes  = commentService.createComment(reqDto, userId, communityId, postId);
        return new BaseResponse<>(commentCreateRes);
    }



}
