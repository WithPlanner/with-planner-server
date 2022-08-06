package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import withplanner.withplanner_api.domain.Category;
import withplanner.withplanner_api.domain.Community;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    //회원님을 위한 습관 모임
    List<Community> findTop6ByCategory(Category category);
    //가장 활성화된 습관 모임
    List<Community> findTop6ByOrderByCurrentCountDesc();
    //최신 습관 모임
    List<Community> findTop6ByOrderByCreatedAtDesc();

    //커뮤니티 검색
    List<Community> findByNameContains(String name);
}
