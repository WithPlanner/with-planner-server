package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import withplanner.withplanner_api.domain.*;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationReq;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationRes;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.repository.CommunityMemberRepository;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.MapRepository;
import withplanner.withplanner_api.repository.UserRepository;

import static withplanner.withplanner_api.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityMemberRepository communityMemberRepository;

    public CommunityCreateLocationRes createLocation(CommunityCreateLocationReq req, Long userId, Long communityId){
        //user
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(NOT_EXISTS_PARTICIPANT));

        //community
        Community community = communityRepository.findById(communityId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_COMMUNITY));

        Map map = Map.builder()
                .x(req.getLongitude())
                .y(req.getLatitude())
                .address(new Address(req.getZipcode(),req.getRoadAddress(),req.getAddress()))
                .build();

        //별칭을 작성한 사용자라면 alias 추가 저장
        if(!req.getAlias().isEmpty()){
            map.addAlias(req.getAlias());
        }

        //map 저장
        mapRepository.save(map);

        //CommunityMember
        CommunityMember communityMember = communityMemberRepository.findCommunityByUserIdAndCommunityId(userId,communityId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_COMMUNITY_MEMBER));

        //communityMember와 map의 연관관계 정의
        communityMember.connectMap(map);

        return CommunityCreateLocationRes.toDto(map);
    }


}
