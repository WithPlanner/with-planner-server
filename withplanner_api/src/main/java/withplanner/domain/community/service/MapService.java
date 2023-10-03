package withplanner.domain.community.service;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.domain.community.repository.CommunityMemberRepository;
import withplanner.domain.community.repository.CommunityRepository;
import withplanner.domain.community.repository.MapRepository;
import withplanner.domain.community.model.Community;
import withplanner.domain.community.model.CommunityMember;
import withplanner.domain.community.model.Map;
import withplanner.domain.post.model.Post;
import withplanner.domain.post.repository.CommentRepository;
import withplanner.domain.user.model.User;
import withplanner.domain.user.repository.UserRepository;
import withplanner.global.dto.community.CommunityAuthenticateLocationReq;
import withplanner.global.dto.community.CommunityAuthenticateLocationRes;
import withplanner.global.dto.community.CommunityCreateLocationReq;
import withplanner.global.dto.community.CommunityCreateLocationRes;
import withplanner.global.dto.community.CommunityMapDto;
import withplanner.global.dto.community.CommunityMapPostDetailRes;
import withplanner.global.dto.community.CommunityUserLocationRes;
import withplanner.global.exception.BaseException;
import withplanner.global.exception.BaseResponseStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static withplanner.global.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapService {
    private final MapRepository mapRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommunityCreateLocationRes createLocation(CommunityCreateLocationReq req, Long userId, Long communityId){
        //user
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(NOT_EXISTS_PARTICIPANT));

        //community
        Community community = communityRepository.findById(communityId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_COMMUNITY));

        GeometryFactory geometryFactory = new GeometryFactory();
        Point coordinate = geometryFactory.createPoint(new Coordinate(req.getLongitude(), req.getLatitude()));

        Map map = Map.builder()
                .coordinate(coordinate)
                .alias(req.getAlias())
                .name(req.getName())
                .build();

        //별칭을 작성한 사용자라면 alias 추가 저장
        if(req.getAlias()!=null){
            map.changeAlias(req.getAlias());
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
        double x = communityMember.getMap().getCoordinate().getX(); //경도
        double y = communityMember.getMap().getCoordinate().getY(); //위도
        String alias = communityMember.getMap().getAlias(); //목적지 별칭
        String roadAddress = "";//communityMember.getMap().getLocation().getRoadAddress(); //도로명 주소
        String address = "";//communityMember.getMap().getLocation().getAddress(); //지번 주소
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

        //커뮤니티에 세팅된 인증 요일 조회
        List<String> days = community.getDays();

        //요청을 보낸 localDateTime이 인증 요일에 해당하는지 확인 - 아니면 Throw
        LocalDate localDate = reqDto.getLocalDateTime().toLocalDate();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String today = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);

        if(!days.contains(today)){
            saveStatus = false;
            throw new BaseException(NOT_AUTHENTICATE_DAY);
        }

        //오늘 이미 인증을 완료했는지 여부 검증
//        List<MapPost> mapPosts = mapPostRepository.findByCommunityIdAndUserId(community.getId(),user.getId());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        Boolean alreadyAuthenticate = mapPosts.stream().anyMatch(
//                mapPost -> LocalDateTime.parse(mapPost.getCreatedAt(),formatter).toLocalDate().isEqual(localDate)
//        );
//        if(alreadyAuthenticate == true){
//            throw new BaseException(ALREADY_AUTHENTICATE_TODAY);
//        }

        //요청을 보낸 localDateTime이 당일인지 여부 확인
        LocalDate now = LocalDate.now();
        if(!now.isEqual(reqDto.getLocalDateTime().toLocalDate())){
            saveStatus = false;
            throw new BaseException(AFTER_AUTHENTICATE_TIME);
        }

        //지정한 시간 이후에 요청을 보내거면
        if(!reqDto.getLocalDateTime().toLocalTime().isBefore(localTime)){
            saveStatus = false;
            throw new BaseException(AFTER_AUTHENTICATE_TIME);
        }

        //거리 계산 값이 False
        if(!reqDto.getAuthenticationStatus().equals(true)){
            saveStatus = false;
            throw new BaseException(CANNOT_AUTHENTICATE_LOCATION);
        }


        Post mapPost = new Post(null,null);

        if(saveStatus==true){
//            mapPost.connectCommunity(community);
//            mapPost.connectUser(user);
//            mapPostRepository.save(mapPost);
        }

//        CommunityAuthenticateLocationRes communityAuthenticateLocationRes = CommunityAuthenticateLocationRes.builder()
//                .mapPostId(mapPost.getId())
//                .userId(mapPost.getUser().getId())
//                .updatedAt(mapPost.getUpdatedAt())
//                .location(communityMemberRepository.findCommunityByUserIdAndCommunityId(mapPost.getUser().getId(),mapPost.getCommunity().getId()).get().getMap().getAlias())
//                .nickName(mapPost.getUser().getNickname())
//                .profileImg(mapPost.getUser().getProfileImg())
//                .build();

        return null;
    }

    //MapPost 전체 가져오기
    @Transactional
    public List<CommunityMapDto> getAllMapPost(Long communityId){

        //커뮤니티 조회
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_COMMUNITY));

        //mapPost 조회
//        List<CommunityMapDto> list =  mapPostRepository.findByCommunityIdOrderByUpdatedAtDesc(community.getId()).stream().map(
//                p -> CommunityMapDto.builder()
//                        .mapPostId(p.getId())
//                        .userId(p.getUser().getId())
//                        .updatedAt(p.getUpdatedAt())
//                        .location(communityMemberRepository.findCommunityByUserIdAndCommunityId(p.getUser().getId(),p.getCommunity().getId()).get().getMap().getAlias())
//                        .nickName(p.getUser().getNickname())
//                        .profileImg(p.getUser().getProfileImg())
//                        .build()
//        ).collect(Collectors.toList());

        return null;
    }

    public CommunityMapPostDetailRes getDetailMapPost(Long userId, Long mapPostId){

        //사용자
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));

        //지도 게시글
//        MapPost mapPost = mapPostRepository.findById(mapPostId)
//                .orElseThrow(()-> new BaseException(BaseResponseStatus.NOT_EXISTS_MAP_POST));

        //댓글 리스트 조회
//        List<CommunityCommentDto> comments = commentRepository.findCommentByMapPostId(mapPost.getId())
//                .stream().map(
//                        p -> CommunityCommentDto.builder()
//                                .commentId(p.getId())
//                                .nickname(p.getUser().getNickname())
//                                .comment(p.getContent())
//                                .createdAt(p.getCreatedAt())
//                                .build()
//                ).collect(Collectors.toList());

//        CommunityMapPostDetailRes communityMapPostDetailRes = CommunityMapPostDetailRes.builder()
//                .mapPostId(mapPost.getId())
//                .userId(mapPost.getUser().getId())
//                .updatedAt(mapPost.getUpdatedAt())
//                .location(communityMemberRepository.findCommunityByUserIdAndCommunityId(mapPost.getUser().getId(),mapPost.getCommunity().getId()).get().getMap().getAlias())
//                .nickName(mapPost.getUser().getNickname())
//                .comments(comments)
//                .build();

        return null;
    }


}
