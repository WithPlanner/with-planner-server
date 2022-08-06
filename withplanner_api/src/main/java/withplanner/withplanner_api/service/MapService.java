package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.*;
import withplanner.withplanner_api.dto.community.*;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.repository.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static withplanner.withplanner_api.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapService {
    private final MapRepository mapRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final MapPostRepository mapPostRepository;


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
                .name(req.getName())
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
        String name = communityMember.getMap().getName(); //상호명

        //도로명 주소가 존재하지 않는 데이터인 경우
        if(roadAddress==null){
            communityUserLocationRes = new CommunityUserLocationRes(nickname,x,y,alias,address,name);
        }
        //도로명 주소가 존재하는 경우
        else {
            communityUserLocationRes = new CommunityUserLocationRes(nickname,x,y,alias,roadAddress,name);
        }

        return communityUserLocationRes;
    }

    @Transactional
    public CommunityAuthenticateLocationRes authenticateLocation(Long userId, Long communityId, CommunityAuthenticateLocationReq reqDto){
        Boolean saveStatus = true;

        //유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));

        //커뮤니티
        Community community = communityRepository.findById(communityId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_COMMUNITY));

        //커뮤니티에 세팅된 인증 시간 조회 (localTime)
        LocalTime localTime = community.getTime();

        //지정한 시간 이후에 요청을 보내거나 거리계산값이 false이면 saveStatus를 false로 변경
        if(!reqDto.getLocalDateTime().toLocalTime().isBefore(localTime)|!reqDto.getAuthenticationStatus().equals(true)){
            saveStatus = false;
        }

        if(saveStatus==true){
            MapPost mapPost = new MapPost();
            mapPost.connectCommunity(community);
            mapPost.connectUser(user);
            mapPostRepository.save(mapPost);
        }

        CommunityAuthenticateLocationRes communityAuthenticateLocationRes = new CommunityAuthenticateLocationRes(saveStatus);
        return communityAuthenticateLocationRes;
    }

}
