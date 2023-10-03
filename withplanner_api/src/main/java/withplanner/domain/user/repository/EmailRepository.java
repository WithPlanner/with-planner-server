package withplanner.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.domain.user.model.EmailAuth;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailAuth, Long> {
    Optional<EmailAuth> findByEmail(String email);
}
