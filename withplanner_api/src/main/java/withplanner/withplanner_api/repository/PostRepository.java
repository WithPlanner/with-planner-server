package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.withplanner_api.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop3ByCommunityIdOrderByCreatedAtDesc(Long communityId);
    List<Post> findByCommunityIdOrderByUpdatedAtDesc(Long communityId);
}
