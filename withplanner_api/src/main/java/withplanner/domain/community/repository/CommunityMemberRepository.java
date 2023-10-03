package withplanner.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import withplanner.domain.community.model.CommunityMember;

import java.util.List;
import java.util.Optional;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
    @Query("select c from CommunityMember c where c.userId = :userId and c.community.id = :communityId")
    Optional<CommunityMember> findCommunityByUserIdAndCommunityId(Long userId, Long communityId);

    List<CommunityMember> findByUserId(Long userId);
}
