package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.withplanner_api.domain.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
