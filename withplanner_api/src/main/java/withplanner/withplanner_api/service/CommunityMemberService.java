package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.domain.CommunityMember;
import withplanner.withplanner_api.domain.Status;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.communityMember.CommunityJoinRes;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.repository.CommunityMemberRepository;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.UserRepository;

import static withplanner.withplanner_api.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CommunityMemberService {
    private final CommunityMemberRepository communityMemberRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityJoinRes joinCommunity(Long communityId, Long userId){
        CommunityMember communityMember = new CommunityMember();

        Boolean authority = false ; //개설자(true),참여자(false) 여부

        Community community =communityRepository.findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_COMMUNITY));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));

        //community의 개설자와 요청(jwt)자가 같으면 true
        if(community.getCreateUser().equals(user)){
            authority = true;
        }
        //현재 인원<최대인원 이면
        if(community.getCurrentCount()<community.getHeadCount()){

            //community의 currentCount +1
            community.plusCurrentCount();

            //Map 정보는 추후 저장 가능하게 할 예정 -> 목적지 설정 다이얼로그 api 하면서 연동할 것.
            communityMember.connectCommunityMember(authority, Status.ACTIVE,community,user);
        }
        else{
            throw new BaseException(EXCEED_HEAD_COUNT);
        }
        communityMemberRepository.save(communityMember);

        return new CommunityJoinRes(community.getId(),user.getId());
    }
}
