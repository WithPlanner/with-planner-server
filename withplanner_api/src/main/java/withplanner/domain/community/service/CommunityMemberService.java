package withplanner.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.domain.community.model.Community;
import withplanner.domain.community.model.CommunityMember;
import withplanner.domain.community.repository.CommunityRepository;
import withplanner.global.entity.Status;
import withplanner.domain.user.model.User;
import withplanner.global.dto.communityMember.CommunityJoinRes;
import withplanner.global.exception.BaseException;
import withplanner.domain.community.repository.CommunityMemberRepository;
import withplanner.domain.user.repository.UserRepository;

import static withplanner.global.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CommunityMemberService {
    private final CommunityMemberRepository communityMemberRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityJoinRes joinCommunity(Long communityId, Long userId){
        CommunityMember communityMember = new CommunityMember();

        boolean authority = false ; //개설자(true),참여자(false) 여부

        Community community =communityRepository.findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_COMMUNITY));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));

        //community의 개설자와 요청(jwt)자가 같으면 true
        if(community.getUserId().equals(userId)){
            authority = true;
        }

        //현재 인원<최대인원 이면
        if(community.getCurrentCount()<community.getMaxCount()){

            //community의 currentCount +1
            community.plusCurrentCount();

            //Map 정보는 추후 저장 가능하게 할 예정 -> 목적지 설정 다이얼로그 api 하면서 연동할 것.
            communityMember.connectCommunityMember(authority, Status.ACTIVE,community,userId);
        }
        else{
            throw new BaseException(EXCEED_HEAD_COUNT);
        }
        communityMemberRepository.save(communityMember);

        return new CommunityJoinRes(community.getId(),user.getId());
    }
}
