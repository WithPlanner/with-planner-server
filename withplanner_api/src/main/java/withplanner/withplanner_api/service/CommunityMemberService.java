package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.communityMember.CommunityJoinRes;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.repository.CommunityMemberRepository;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.UserRepository;

import static withplanner.withplanner_api.exception.BaseResponseStatus.NOT_EXISTS_COMMUNITY;
import static withplanner.withplanner_api.exception.BaseResponseStatus.NOT_EXISTS_PARTICIPANT;

@Service
@RequiredArgsConstructor
public class CommunityMemberService {
    private final CommunityMemberRepository communityMemberRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityJoinRes joinCommunity(Long communityId, Long userId){
        Community community =communityRepository.findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_COMMUNITY));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));

        //community의 개설자와 요청(jwt)자가 같으면 방장여부 처리
        if(community.getCreateUser().equals(user)){

        }
        //User 정보 저장.
        //Community 정보 저장.

        return new CommunityJoinRes(community.getId(),user.getId());
    }
}
