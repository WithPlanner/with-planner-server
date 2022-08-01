package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.domain.Type;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;
import withplanner.withplanner_api.dto.community.CommunityResp;
import withplanner.withplanner_api.dto.post.ListCardResp;
import withplanner.withplanner_api.dto.post.MainListResp;
import withplanner.withplanner_api.dto.post.PostCardResp;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.exception.BaseResponseStatus;
import withplanner.withplanner_api.repository.CommunityMemberRepository;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.PostRepository;
import withplanner.withplanner_api.repository.UserRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommunityMemberRepository communityMemberRepository;
//    private final S3Service s3Service;

    public Long createMapCommunity(CommunityMakeReq reqDto, String username) {
        Community community;

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        if (reqDto.getCommunityImg() != null) {
//            String name = s3Service.uploadToAWS(reqDto.getCommunityImg());
            //s3에 저장된 imgUrl 이를 저장하면 된다.
//            String imgUrl = "https://gogumacat-s3.s3.ap-northeast-2.amazonaws.com/" + name;
            String imgUrl = "test";
            community = Community.builder()
                    .name(reqDto.getName())
                    .introduce(reqDto.getIntroduce())
                    .communityImg(imgUrl)
                    .headCount(reqDto.getHeadCount())
                    .category(reqDto.getCategory())
                    .days(reqDto.getDay())
                    .time(reqDto.getTime())
                    .type(Type.mapPost)
                    .build();
        } else {
            community = Community.builder()
                    .name(reqDto.getName())
                    .introduce(reqDto.getIntroduce())
                    .headCount(reqDto.getHeadCount())
                    .category(reqDto.getCategory())
                    .days(reqDto.getDay())
                    .time(reqDto.getTime())
                    .type(Type.mapPost)
                    .build();
        }

        user.addCommunity(community);

        return communityRepository.save(community).getId();
    }

    public Long createPostCommunity(CommunityMakeReq reqDto, String username) {
        Community community;

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        if (reqDto.getCommunityImg() != null) {
//            String name = s3Service.uploadToAWS(reqDto.getCommunityImg());
            //s3에 저장된 imgUrl 이를 저장하면 된다.
//            String imgUrl = "https://gogumacat-s3.s3.ap-northeast-2.amazonaws.com/" + name;
            String imgUrl = "test";
            community = Community.builder()
                    .name(reqDto.getName())
                    .introduce(reqDto.getIntroduce())
                    .communityImg(imgUrl)
                    .headCount(reqDto.getHeadCount())
                    .category(reqDto.getCategory())
                    .days(reqDto.getDay())
                    .time(reqDto.getTime())
                    .type(Type.post)
                    .build();
        } else {
            community = Community.builder()
                    .name(reqDto.getName())
                    .introduce(reqDto.getIntroduce())
                    .headCount(reqDto.getHeadCount())
                    .category(reqDto.getCategory())
                    .days(reqDto.getDay())
                    .time(reqDto.getTime())
                    .type(Type.post)
                    .build();
        }

        user.addCommunity(community);

        return communityRepository.save(community).getId();
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
                        .build()
        ).collect(Collectors.toList());

        return CommunityResp.builder()
                .communityId(community.getId())
                .name(community.getName())
                .createdAt(community.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .introduce(community.getIntroduce())
                .communityImg(community.getCommunityImg())
                .category(community.getCategory().toString())
                .headCount(community.getHeadCount())
                .currentCount(community.getCurrentCount())
                .posts(posts)
                .build();


    }

    public MainListResp mainListing(User user) {
        //회원님을 위한 습관 모임
        List<ListCardResp> recommendList = communityRepository.findTop6ByCategory(user.getRecommend()).stream().map(
                c -> ListCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .build()
        ).collect(Collectors.toList());

        //회원님이 참여하는 습관 모임
        List<ListCardResp> myList = user.getCommunities().stream().map(
                c -> ListCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .build()
        ).collect(Collectors.toList());

        //가장 활성화된 습관 모임
        List<ListCardResp> hotList = communityRepository.findTop6ByOrderByCurrentCountDesc().stream().map(
                c -> ListCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .build()
        ).collect(Collectors.toList());

        //최신 습관 모임
        List<ListCardResp> newList = communityRepository.findTop6ByOrderByCreatedAtDesc().stream().map(
                c -> ListCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .build()
        ).collect(Collectors.toList());

        return new MainListResp(recommendList, myList, hotList, newList);
    }

    public List<ListCardResp> searchCommunity(String query) {
        return communityRepository.findByNameContains(query).stream().map(
                c -> ListCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .build()
        ).collect(Collectors.toList());
    }
}
