package withplanner.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.domain.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
