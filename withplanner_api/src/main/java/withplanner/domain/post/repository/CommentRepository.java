package withplanner.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import withplanner.domain.post.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.status = 'ACTIVE' and c.userId =:userId and c.post.id = :postId ")
    List<Comment> findCommentByUserIdAndPostId(Long userId, Long postId);

    List<Comment> findCommentByPostId(Long postId);

}
