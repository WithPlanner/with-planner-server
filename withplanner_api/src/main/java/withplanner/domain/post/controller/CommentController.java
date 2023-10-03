package withplanner.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import withplanner.domain.user.model.User;
import withplanner.global.dto.comment.CommentCreateReq;
import withplanner.global.dto.comment.CommentCreateRes;
import withplanner.global.exception.BaseResponse;
import withplanner.domain.post.service.CommentService;

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
