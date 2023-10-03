package withplanner.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.domain.post.model.PostImage;

public interface PostImgRepository extends JpaRepository<PostImage, Long> {
}
