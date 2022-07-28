package withplanner.withplanner_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import withplanner.withplanner_api.domain.CommunityMember;

public interface CommunityMemberRepository  extends JpaRepository<CommunityMember, Long> {
}
