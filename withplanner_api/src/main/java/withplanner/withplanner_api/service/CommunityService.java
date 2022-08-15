package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.*;
import withplanner.withplanner_api.dto.community.*;
import withplanner.withplanner_api.dto.post.ListCardResp;
import withplanner.withplanner_api.dto.post.MainListResp;
import withplanner.withplanner_api.dto.post.PostCardResp;
import withplanner.withplanner_api.dto.post.SearchCardResp;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.exception.BaseResponseStatus;
import withplanner.withplanner_api.repository.CommunityMemberRepository;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.PostRepository;
import withplanner.withplanner_api.repository.UserRepository;
import withplanner.withplanner_api.util.AuthEmailSender;
import withplanner.withplanner_api.util.RecommendCategory;
import withplanner.withplanner_api.util.S3Service;
import withplanner.withplanner_api.repository.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import static withplanner.withplanner_api.exception.BaseResponseStatus.NOT_EXISTS_COMMUNITY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final S3Service s3Service;
    private final MapPostRepository mapPostRepository;
    private final AuthEmailSender authMailSender;

    @Transactional
    public CommunityCreateRes createMapCommunity(CommunityMakeReq reqDto, String username) {
        Community community;
        String imgUrl = "https://withplanner-s3.s3.ap-northeast-2.amazonaws.com/default_communityImg_transparency.png";
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        if (reqDto.getCommunityImg() != null) {
            String name = s3Service.uploadToAWS(reqDto.getCommunityImg());
            //s3에 저장된 imgUrl 이를 저장하면 된다.
            imgUrl = "https://withplanner-s3.s3.ap-northeast-2.amazonaws.com/" + name;
        }

        //공개 커뮤니티
        if(reqDto.getPublicType() .equals(PublicType.publicType.toString())){
            community = Community.builder()
                    .name(reqDto.getName())
                    .introduce(reqDto.getIntroduce())
                    .communityImg(imgUrl)
                    .headCount(reqDto.getHeadCount())
                    .category(reqDto.getCategory())
                    .days(reqDto.getDay())
                    .time(reqDto.getTime())
                    .type(Type.mapPost)
                    .publicType(PublicType.publicType)
                    .build();
        }
        else{
            String password = createPassword();

            community = Community.builder()
                    .name(reqDto.getName())
                    .introduce(reqDto.getIntroduce())
                    .communityImg(imgUrl)
                    .headCount(reqDto.getHeadCount())
                    .category(reqDto.getCategory())
                    .days(reqDto.getDay())
                    .time(reqDto.getTime())
                    .type(Type.mapPost)
                    .publicType(PublicType.privateType)
                    .password(password)
                    .build();
        }
        user.addCommunity(community);

        CommunityMember communityMember = new CommunityMember();
        communityMember.connectCommunityMember(true, Status.ACTIVE, community, user);
        communityMemberRepository.save(communityMember);
        Long communityId = communityRepository.save(community).getId();

        //private 이라면 커뮤니티 비밀번호 방장에게 발송
        if(community.getPublicType().equals(PublicType.privateType)){
            sendPasswordEmail(user.getEmail(),community.getPassword());
        }

        return new CommunityCreateRes(communityId, "커뮤니티를 생성하였습니다.",community.getPublicType().toString());
    }
    //커뮤니티 비밀번호
    private void sendPasswordEmail(String email,String password) {
        authMailSender.sendMail2(email, password);
    }

    @Transactional
    public CommunityCreateRes createPostCommunity(CommunityMakeReq reqDto, String username) {
        Community community;
        System.out.println("reqDto = " + reqDto);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        if (reqDto.getCommunityImg() != null) {
            String name = s3Service.uploadToAWS(reqDto.getCommunityImg());
            //s3에 저장된 imgUrl 이를 저장하면 된다.
            String imgUrl = "https://withplanner-s3.s3.ap-northeast-2.amazonaws.com/" + name;

            //사진을 저장O & public type
            if(reqDto.getPublicType().equals(PublicType.publicType.toString())) {
                community = Community.builder()
                        .name(reqDto.getName())
                        .introduce(reqDto.getIntroduce())
                        .communityImg(imgUrl)
                        .headCount(reqDto.getHeadCount())
                        .category(reqDto.getCategory())
                        .days(reqDto.getDay())
                        .time(reqDto.getTime())
                        .type(Type.post)
                        .publicType(PublicType.publicType)
                        .build();
            }
            //사진을 저장O & private type
            else {
                String password = createPassword();

                community = Community.builder()
                        .name(reqDto.getName())
                        .introduce(reqDto.getIntroduce())
                        .communityImg(imgUrl)
                        .headCount(reqDto.getHeadCount())
                        .category(reqDto.getCategory())
                        .days(reqDto.getDay())
                        .time(reqDto.getTime())
                        .type(Type.post)
                        .publicType(PublicType.privateType)
                        .password(password)
                        .build();
            }

        } else {
            //사진을 저장X & public type
            if(reqDto.getPublicType().equals(PublicType.publicType.toString())) {
                community = Community.builder()
                        .name(reqDto.getName())
                        .introduce(reqDto.getIntroduce())
                        .headCount(reqDto.getHeadCount())
                        .category(reqDto.getCategory())
                        .days(reqDto.getDay())
                        .time(reqDto.getTime())
                        .type(Type.post)
                        .publicType(PublicType.publicType)
                        .build();
            }
            //사진을 저장X & private type
            else  {

                String password = createPassword();

                community = Community.builder()
                        .name(reqDto.getName())
                        .introduce(reqDto.getIntroduce())
                        .headCount(reqDto.getHeadCount())
                        .category(reqDto.getCategory())
                        .days(reqDto.getDay())
                        .time(reqDto.getTime())
                        .type(Type.post)
                        .publicType(PublicType.privateType)
                        .password(password)
                        .build();
            }
        }

        user.addCommunity(community);

        CommunityMember communityMember = new CommunityMember();
        communityMember.connectCommunityMember(true, Status.ACTIVE, community, user);
        communityMemberRepository.save(communityMember);

        Long communityId = communityRepository.save(community).getId();

        //private 이라면 커뮤니티 비밀번호 방장에게 발송
        if(community.getPublicType().equals(PublicType.privateType)){
            sendPasswordEmail(user.getEmail(),community.getPassword());
        }

        return new CommunityCreateRes(communityId, "커뮤니티를 생성하였습니다.", community.getPublicType().toString());
    }

    public CommunityResp getPostCommunityMain(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_COMMUNITY));

        //community의 최신 게시물 3개 가져오기
        List<PostCardResp> posts = postRepository.findTop3ByCommunityIdOrderByCreatedAtDesc(community.getId()).stream().map(
                p -> PostCardResp.builder()
                        .postId(p.getId())
                        .name(p.getName())
                        .content(p.getContent())
                        .images(p.getImages())
                        .writerNickname(p.getUser().getNickname())
                        .updatedAt(p.getUpdatedAt())
                        .build()
        ).collect(Collectors.toList());

        return CommunityResp.builder()
                .communityId(community.getId())
                .name(community.getName())
                .createdAt(community.getCreatedAt())
                .introduce(community.getIntroduce())
                .communityImg(community.getCommunityImg())
                .category(community.getCategory().toString())
                .headCount(community.getHeadCount())
                .currentCount(community.getCurrentCount())
                .days(community.getDays())
                .time(community.getTime().toString())
                .posts(posts)
                .type(community.getType().toString())
                .updatedAt(community.getUpdatedAt())
                .build();
    }

    //Map 커뮤니티 메인 조회
    @Transactional
    public CommunityMapRes getMapPostCommunityMain(Long communityId){

        //커뮤니티 조회
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_COMMUNITY));

        //mapPost 조회
        List<CommunityMapDto> list =  mapPostRepository.findTop6ByCommunityIdOrderByCreatedAtDesc(community.getId()).stream().map(
                p -> CommunityMapDto.builder()
                        .mapPostId(p.getId())
                        .userId(p.getUser().getId())
                        .updatedAt(p.getUpdatedAt())
                        .location(communityMemberRepository.findCommunityByUserIdAndCommunityId(p.getUser().getId(),p.getCommunity().getId()).get().getMap().getAlias())
                        .nickName(p.getUser().getNickname())
                        .profileImg(p.getUser().getProfileImg())
                        .build()
        ).collect(Collectors.toList());

        //res로 변환
        CommunityMapRes communityMapRes = CommunityMapRes
                .builder()
                .mapPosts(list)
                .communityId(community.getId())
                .name(community.getName())
                .createdAt(community.getCreatedAt())
                .updatedAt(community.getUpdatedAt())
                .introduce(community.getIntroduce())
                .communityImg(community.getCommunityImg())
                .category(community.getCategory().toString())
                .headCount(community.getHeadCount())
                .currentCount(community.getCurrentCount())
                .time(community.getTime())
                .days(community.getDays())
                .build(); ;

        return communityMapRes;
    }

    @Transactional
    public MainListResp mainListing(User user) {
        //회원님을 위한 습관 모임
        String recommend = RecommendCategory.recommend(user.getRecommend());
        List<Long> myCommunityId = new ArrayList<>();

        List<ListCardResp> recommendList = communityRepository.findTop6ByCategory(Category.valueOf(recommend)).stream().map(
                c -> ListCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .type(c.getType().toString())
                        .category(c.getCategory().toString())
                        .build()
        ).collect(Collectors.toList());

        //회원님이 참여하는 습관 모임
        List<Community> myCommunities = communityMemberRepository.findByUserId(user.getId()).stream().map(
                CommunityMember::getCommunity
        ).collect(Collectors.toList());

        List<ListCardResp> myList = myCommunities.stream().map(
                c -> {
                    myCommunityId.add(c.getId());
                    return ListCardResp.builder()
                            .communityId(c.getId())
                            .name(c.getName())
                            .communityImg(c.getCommunityImg())
                            .type(c.getType().toString())
                            .category(c.getCategory().toString())
                            .build();
                }).collect(Collectors.toList());


        //가장 활성화된 습관 모임
        List<ListCardResp> hotList = communityRepository.findTop6ByOrderByCurrentCountDesc().stream().map(
                c -> ListCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .type(c.getType().toString())
                        .category(c.getCategory().toString())
                        .build()
        ).collect(Collectors.toList());

        //최신 습관 모임
        List<ListCardResp> newList = communityRepository.findTop6ByOrderByCreatedAtDesc().stream().map(
                c -> ListCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .type(c.getType().toString())
                        .category(c.getCategory().toString())
                        .build()
        ).collect(Collectors.toList());


        deleteDup(myCommunityId, recommendList);
        deleteDup(myCommunityId, hotList);
        deleteDup(myCommunityId, newList);

        return new MainListResp(recommendList, myList, hotList, newList);

    }


    public List<SearchCardResp> searchCommunity(User user, String query) {

        List<Long> myCommunityId = new ArrayList<>();


        //회원님이 참여하는 습관 모임
        List<Community> myCommunities = communityMemberRepository.findByUserId(user.getId()).stream().map(
                CommunityMember::getCommunity
        ).collect(Collectors.toList());


        List<ListCardResp> myList = myCommunities.stream().map(
                c -> {
                    myCommunityId.add(c.getId());
                    return ListCardResp.builder()
                            .communityId(c.getId())
                            .name(c.getName())
                            .communityImg(c.getCommunityImg())
                            .type(c.getType().toString())
                            .category(c.getCategory().toString())
                            .build();
                }).collect(Collectors.toList());


        List<SearchCardResp> searchList =  communityRepository.findByNameContains(query).stream().map(
                c -> SearchCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .type(c.getType().toString())
                        .category(c.getCategory().toString())
                        .publicType(c.getPublicType().toString())
                        .build()
        ).collect(Collectors.toList());

        return searchDeleteDup(myCommunityId,searchList);
    }

    @Transactional
    public CommunityGetInfoRes getCommunityInfo(Long communityId){

        //community 조회
        Community community = communityRepository.findById(communityId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_COMMUNITY));

        CommunityGetInfoRes communityGetInfoRes = CommunityGetInfoRes.toDto(community);
        return communityGetInfoRes;
    }

    //만약 비교리스트에 내가 참여한 커뮤니티가 포함되어있으면 삭제
    private List<ListCardResp> deleteDup(List<Long> myCommunityId, List<ListCardResp> compareList) {
        compareList.removeIf(c -> myCommunityId.contains(c.getCommunityId()));
        return compareList;
    }

    //(검색 버전)
    //만약 비교리스트에 내가 참여한 커뮤니티가 포함되어있으면 삭제
    private List<SearchCardResp> searchDeleteDup(List<Long> myCommunityId, List<SearchCardResp> compareList) {
        compareList.removeIf(c -> myCommunityId.contains(c.getCommunityId()));
        return compareList;
    }

    //난수 생성
    private String createPassword(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

}
