package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.domain.Type;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.exception.BaseResponseStatus;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
//    private final S3Service s3Service;

    public Long createMapCommunity(CommunityMakeReq reqDto, String usernmae) {
        Community community;

        User user = userRepository.findByEmail(usernmae)
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

    public Long createPostCommunity(CommunityMakeReq reqDto, String usernmae) {
        Community community;

        User user = userRepository.findByEmail(usernmae)
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
}
