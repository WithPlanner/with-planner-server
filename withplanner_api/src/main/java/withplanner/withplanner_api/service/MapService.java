package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.*;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationReq;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationRes;
import withplanner.withplanner_api.dto.community.CommunityUserLocationRes;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.repository.CommunityMemberRepository;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.MapRepository;
import withplanner.withplanner_api.repository.UserRepository;

import static withplanner.withplanner_api.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapService {
    private final MapRepository mapRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityMemberRepository communityMemberRepository;

    @Transactional
    public CommunityCreateLocationRes createLocation(CommunityCreateLocationReq req, Long userId, Long communityId){
        //user
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(NOT_EXISTS_PARTICIPANT));

        //community
        Community community = communityRepository.findById(communityId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_COMMUNITY));

        System.out.println("아아" +req.getLongitude());

        Map map = Map.builder()
                .x(req.getLongitude())
                .y(req.getLatitude())
                .address(new Address(req.getZipcode(),req.getRoadAddress(),req.getAddress()))
                .alias(req.getAlias())
                .build();

        //별칭을 작성한 사용자라면 alias 추가 저장
        if(req.getAlias()!=null){
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

    @Transactional
    public CommunityUserLocationRes getUserLocation(Long userId, Long communityId){

        CommunityUserLocationRes communityUserLocationRes = null;

        //유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));

        //커뮤니티 조회
        CommunityMember communityMember = communityMemberRepository.findCommunityByUserIdAndCommunityId(userId,communityId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_COMMUNITY_MEMBER));

        String nickname = user.getNickname(); //유저 닉네임
        double x = communityMember.getMap().getX(); //경도
        double y= communityMember.getMap().getY(); //위도
        String alias = communityMember.getMap().getAlias(); //목적지 별칭
        String roadAddress = communityMember.getMap().getAddress().getBaseAddress(); //도로명 주소
        String address = communityMember.getMap().getAddress().getDetailedAddress(); //지번 주소

        //도로명 주소가 존재하지 않는 데이터인 경우
        if(roadAddress==null){
            communityUserLocationRes = new CommunityUserLocationRes(nickname,x,y,alias,address);
        }
        //도로명 주소가 존재하는 경우
        else {
            communityUserLocationRes = new CommunityUserLocationRes(nickname,x,y,alias,roadAddress);
        }

        return communityUserLocationRes;
    }

}
