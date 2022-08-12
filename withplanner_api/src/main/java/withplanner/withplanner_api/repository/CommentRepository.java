package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.withplanner_api.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
