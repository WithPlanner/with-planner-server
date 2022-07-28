package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.domain.Type;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;
import withplanner.withplanner_api.repository.CommunityRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final S3Service s3Service;
    public Long createMapCoummunity(CommunityMakeReq reqDto) {
        Community community;
        if (reqDto.getCommunityImg() != null) {
            String name = s3Service.uploadToAWS(reqDto.getCommunityImg());
            //s3에 저장된 imgUrl 이를 저장하면 된다.
            String imgUrl = "https://gogumacat-s3.s3.ap-northeast-2.amazonaws.com/" + name;
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

        return communityRepository.save(community).getId();
    }
}
