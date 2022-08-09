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
import withplanner.withplanner_api.dto.post.PostCardResp;
import withplanner.withplanner_api.dto.post.PostCreateReq;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.PostImgRepository;
import withplanner.withplanner_api.repository.PostRepository;
import withplanner.withplanner_api.repository.UserRepository;
import withplanner.withplanner_api.util.S3Service;

import java.util.List;
import java.util.stream.Collectors;

import static withplanner.withplanner_api.exception.BaseResponseStatus.NOT_EXISTS_COMMUNITY;
import static withplanner.withplanner_api.exception.BaseResponseStatus.NOT_EXISTS_PARTICIPANT;

@Service
@RequiredArgsConstructor
public class PostService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostImgRepository postImgRepository;
    private final S3Service s3Service;

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
        System.out.println("reqDto.getImg() = " + reqDto.getImg());
        if (reqDto.getImg() != null) {
            String name = s3Service.uploadToAWS(reqDto.getImg());
            String imgUrl = "https://withplanner-s3.s3.ap-northeast-2.amazonaws.com/" + name;
            PostImg postImg = new PostImg(imgUrl);
            postImgRepository.save(postImg);
            post.addPostImg(postImg);
        }

        user.addPost(post);
        community.addPost(post);

        Post savedPost = postRepository.save(post);

        return new ResultLongResp(savedPost.getId(), "글 작성에 성공하였습니다.");
    }

    public List<PostCardResp> getAllPost(Long communityId){
       return  postRepository.findByCommunityId(communityId).stream().map(
                p -> PostCardResp.builder()
                        .postId(p.getId())
                        .name(p.getName())
                        .content(p.getContent())
                        .images(p.getImages())
                        .writerNickname(p.getUser().getNickname())
                        .build()
        ).collect(Collectors.toList());
    }
}
