package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.PostImg;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {
    private final S3Service s3Service;
    public Long createMapCoummunity(CommunityMakeReq reqDto) {
        if (reqDto.getCommunityImg() != null) {
            String name = s3Service.uploadToAWS(reqDto.getCommunityImg());
            //s3에 저장된 imgUrl 이를 저장하면 된다.
            String imgUrl = "https://gogumacat-s3.s3.ap-northeast-2.amazonaws.com/" + name;
        }
        return 1L;
    }
}
