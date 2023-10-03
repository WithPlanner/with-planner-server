package withplanner.global.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import withplanner.domain.post.model.Comment;


@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateRes {

    private Long commentId; //commentId
    private String nickname; //닉네임
    private String comment; //댓글 내용

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String createdAt;//댓글 생성 날짜

    @Builder
    static public CommentCreateRes toDto(Comment comment, String nickname){
        return CommentCreateRes.builder()
                .commentId(comment.getId())
                .nickname(nickname)
                .comment(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}
