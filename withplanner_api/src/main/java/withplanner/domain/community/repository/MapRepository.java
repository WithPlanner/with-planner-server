package withplanner.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.domain.community.model.Map;

public interface MapRepository extends JpaRepository<Map, Long> {

}
