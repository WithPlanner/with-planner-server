package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import withplanner.withplanner_api.domain.Map;

import java.util.List;

public interface MapRepository extends JpaRepository<Map, Long> {

}
