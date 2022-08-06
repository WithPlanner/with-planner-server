package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.withplanner_api.domain.MapPost;

public interface MapPostRepository extends JpaRepository<MapPost, Long> {
}
