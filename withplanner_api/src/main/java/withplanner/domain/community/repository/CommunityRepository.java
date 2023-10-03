package withplanner.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.domain.community.model.Category;
import withplanner.domain.community.model.Community;


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
