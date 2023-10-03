package withplanner.global.dto.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import withplanner.domain.post.model.Comment;
import withplanner.global.dto.comment.CommentCreateRes;

@Getter
@Setter
@Builder
public class CommunityCommentDto {

    private Long commentId; //commentId
    private String nickname; //닉네임
    private String comment; //댓글 내용

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String createdAt;//댓글 생성 날짜


    static public CommentCreateRes toDto(Comment comment, String nickname){
        return CommentCreateRes.builder()
                .commentId(comment.getId())
                .nickname(nickname)
                .comment(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}
