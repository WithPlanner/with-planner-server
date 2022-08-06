package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.withplanner_api.domain.PostImg;

public interface PostImgRepository extends JpaRepository<PostImg, Long> {
}
