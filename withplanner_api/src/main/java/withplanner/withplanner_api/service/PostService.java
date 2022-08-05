package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.domain.Post;
import withplanner.withplanner_api.domain.PostImg;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.ResultLongResp;
import withplanner.withplanner_api.dto.ResultMsgResp;
import withplanner.withplanner_api.dto.post.MainListResp;
import withplanner.withplanner_api.dto.post.PostCreateReq;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.PostImgRepository;
import withplanner.withplanner_api.repository.PostRepository;
import withplanner.withplanner_api.repository.UserRepository;

import static withplanner.withplanner_api.exception.BaseResponseStatus.NOT_EXISTS_COMMUNITY;
import static withplanner.withplanner_api.exception.BaseResponseStatus.NOT_EXISTS_PARTICIPANT;

@Service
@RequiredArgsConstructor
public class PostService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;
//    private final S3Service s3Service;

    @Transactional
    public ResultLongResp createPost(PostCreateReq reqDto, Long communityId, String username) {

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_COMMUNITY));

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        Post post = Post.builder()
                .name(reqDto.getName())
                .content(reqDto.getContent())
                .build();

        //연관관계 매핑

        //사진이 있을 경우 이미저 저장해 연관관계 매핑
        if (reqDto.getImg() != null) {
            PostImg postImg = savePostImg(reqDto.getImg());
            postImgRepository.save(postImg);
            post.addPostImg(postImg);
        }

        user.addPost(post);
        community.addPost(post);

        Post savedPost = postRepository.save(post);

        return new ResultLongResp(savedPost.getId(), "글 작성에 성공하였습니다.");
    }

    public PostImg savePostImg(MultipartFile imgFile) {
//        String name = s3Service.uploadToAWS(imgFile);
        //s3에 저장된 imgUrl 이를 저장하면 된다.
//            String imgUrl = "https://gogumacat-s3.s3.ap-northeast-2.amazonaws.com/" + name;
        String imgUrl = "test";
        return new PostImg(imgUrl);
    }
}
