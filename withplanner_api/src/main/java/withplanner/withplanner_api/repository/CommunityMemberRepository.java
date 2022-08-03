package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import withplanner.withplanner_api.domain.CommunityMember;

import java.util.Optional;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
    @Query("select c from CommunityMember c where c.user.id = :userId and c.community.id = :communityId")
    Optional<CommunityMember> findCommunityByUserIdAndCommunityId(Long userId, Long communityId);
}
