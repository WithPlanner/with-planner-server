package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import withplanner.withplanner_api.dto.communityMember.CommunityJoinRes;
import withplanner.withplanner_api.repository.CommunityMemberRepository;
import withplanner.withplanner_api.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CommunityMemberService {
    private final CommunityMemberRepository communityMemberRepository;
    private final UserRepository userRepository;


    public void joinCommunity(Long communityId, Long userId){

    }
}
