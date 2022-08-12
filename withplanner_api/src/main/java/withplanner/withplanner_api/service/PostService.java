package withplanner.withplanner_api.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.*;
import withplanner.withplanner_api.dto.ResultLongResp;
import withplanner.withplanner_api.dto.community.CommunityCommentDto;
import withplanner.withplanner_api.dto.community.CommunityPostDetailRes;
import withplanner.withplanner_api.dto.post.PostCardResp;
import withplanner.withplanner_api.dto.post.PostCreateReq;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.exception.BaseResponseStatus;
import withplanner.withplanner_api.repository.*;
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
    private final CommentRepository commentRepository;

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
       return  postRepository.findByCommunityIdOrderByUpdatedAtDesc(communityId).stream().map(
                p -> PostCardResp.builder()
                        .postId(p.getId())
                        .name(p.getName())
                        .content(p.getContent())
                        .images(p.getImages())
                        .writerNickname(p.getUser().getNickname())
                        .build()
        ).collect(Collectors.toList());
    }

    public CommunityPostDetailRes getDetailPost(Long userId, Long postIdx){
        //글 작성자와 로그인 한 유저 여부 비교
        Boolean authorStatus = false;

        Post post = postRepository.findById(postIdx)
                .orElseThrow(()->new BaseException(BaseResponseStatus.NOT_EXISTS_POST));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));

        if(post.getUser().equals(user)){
            authorStatus = true;
        }

        List<CommunityCommentDto> comments = commentRepository.findCommentByPostId(post.getId())
                .stream().map(
                        p -> CommunityCommentDto.builder()
                                .commentId(p.getId())
                                .nickname(p.getUser().getNickname())
                                .comment(p.getContent())
                                .createdAt(p.getCreatedAt())
                                .build()
        ).collect(Collectors.toList());

        CommunityPostDetailRes communityPostDetailRes = CommunityPostDetailRes.builder()
                .postId(post.getId())
                .name(post.getName())
                .content(post.getContent())
                .images(post.getImages())
                .writerNickname(post.getUser().getNickname())
                .updatedAt(post.getCreatedAt())
                .comments(comments)
                .authorStatus(authorStatus)
                .build();
        return communityPostDetailRes;

    }
}
