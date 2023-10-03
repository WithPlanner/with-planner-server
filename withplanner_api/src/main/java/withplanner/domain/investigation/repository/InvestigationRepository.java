package withplanner.domain.investigation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.domain.investigation.model.Investigation;

public interface InvestigationRepository extends JpaRepository<Investigation, Long> {

}
