package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.withplanner_api.domain.Investigation;

public interface InvestigationRepository extends JpaRepository<Investigation, Long> {

}
