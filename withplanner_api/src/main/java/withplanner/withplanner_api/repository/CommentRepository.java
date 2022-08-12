package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import withplanner.withplanner_api.domain.Comment;
import withplanner.withplanner_api.domain.CommunityMember;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.status = 'ACTIVE' and c.user.id =:userId and c.post.id = :postId ")
    List<Comment> findCommentByUserIdAndPostId(Long userId, Long postId);

    List<Comment> findCommentByPostId(Long postId);
}
