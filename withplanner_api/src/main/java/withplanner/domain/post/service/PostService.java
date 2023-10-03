package withplanner.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.domain.community.repository.CommunityRepository;
import withplanner.domain.post.model.Post;
import withplanner.domain.post.model.PostImage;
import withplanner.domain.post.repository.CommentRepository;
import withplanner.domain.post.repository.PostImgRepository;
import withplanner.domain.post.repository.PostRepository;
import withplanner.domain.user.model.User;
import withplanner.domain.community.model.Community;
import withplanner.domain.user.repository.UserRepository;
import withplanner.global.dto.community.CommunityCommentDto;
import withplanner.global.dto.community.CommunityPostDetailRes;
import withplanner.global.dto.post.PostCardResp;
import withplanner.global.dto.post.PostCreateReq;
import withplanner.global.exception.BaseException;
import withplanner.global.exception.BaseResponseStatus;
import withplanner.global.util.S3Service;

import java.util.List;
import java.util.stream.Collectors;

import static withplanner.global.exception.BaseResponseStatus.NOT_EXISTS_COMMUNITY;
import static withplanner.global.exception.BaseResponseStatus.NOT_EXISTS_PARTICIPANT;

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
    public PostCardResp createPost(PostCreateReq reqDto, Long communityId, String username) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_COMMUNITY));

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        Post post = Post.builder()
                .name(reqDto.getName())
                .content(reqDto.getContent())
                .build();

        //사진이 있을 경우 이미저 저장해 연관관계 매핑
        if (reqDto.getImg() != null) {
            String name = s3Service.uploadToAWS(reqDto.getImg());
            String imgUrl = "https://withplanner-s3.s3.ap-northeast-2.amazonaws.com/" + name;
            PostImage postImage = new PostImage(imgUrl);
            postImgRepository.save(postImage);
            post.addPostImg(postImage);
        }

        user.addPost(post);
        community.addPost(post);

        Post savedPost = postRepository.save(post);

        PostCardResp postCardResp = PostCardResp.builder()
                .postId(savedPost.getId())
                .name(savedPost.getName())
                .content(savedPost.getContent())
                .images(savedPost.getImages())
//                .writerNickname(savedPost.getUser().getNickname())
                .updatedAt(savedPost.getUpdatedAt())
                .build();

        return postCardResp;
    }

    public List<PostCardResp> getAllPost(Long communityId){
       return  postRepository.findByCommunityIdOrderByUpdatedAtDesc(communityId).stream().map(
                p -> PostCardResp.builder()
                        .postId(p.getId())
                        .name(p.getName())
                        .content(p.getContent())
                        .images(p.getImages())
//                        .writerNickname(p.getUser().getNickname())
                        .updatedAt(p.getUpdatedAt())
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

//        //todo : 단방향으로 바꾸면서 컴파일 에러 없애기 위해 주석처리
//        if(post.getUser().equals(user)){
//            authorStatus = true;
//        }

        List<CommunityCommentDto> comments = commentRepository.findCommentByPostId(post.getId())
                .stream().map(
                        p -> CommunityCommentDto.builder()
                                .commentId(p.getId())
//                                .nickname(p.getUser().getNickname()) //todo : 나중에 재반영 해야한다.
                                .comment(p.getContent())
                                .createdAt(p.getCreatedAt())
                                .build()
        ).collect(Collectors.toList());

        CommunityPostDetailRes communityPostDetailRes = CommunityPostDetailRes.builder()
                .postId(post.getId())
                .name(post.getName())
                .content(post.getContent())
                .images(post.getImages())
//                .writerNickname(post.getUser().getNickname())
                .updatedAt(post.getCreatedAt())
                .comments(comments)
                .authorStatus(authorStatus)
                .build();
        return communityPostDetailRes;

    }
}
