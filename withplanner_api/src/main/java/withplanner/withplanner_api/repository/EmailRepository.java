package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.withplanner_api.domain.EmailAuth;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailAuth, Long> {
    Optional<EmailAuth> findByEmail(String email);
}
