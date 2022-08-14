package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.withplanner_api.domain.MapPost;
import withplanner.withplanner_api.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface MapPostRepository extends JpaRepository<MapPost, Long> {
    List<MapPost> findTop6ByCommunityIdOrderByCreatedAtDesc(Long communityId);
    List<MapPost> findByCommunityIdOrderByUpdatedAtDesc(Long communityId);
    boolean existsByCommunityIdAndUserIdAndCreatedAt(Long communityId, Long userId, LocalDateTime createdAt);
}
