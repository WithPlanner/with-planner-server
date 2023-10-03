package withplanner.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import withplanner.domain.community.repository.CommunityRepository;
import withplanner.domain.post.model.Comment;
import withplanner.domain.post.model.Post;
import withplanner.domain.post.repository.CommentRepository;
import withplanner.domain.post.repository.PostRepository;
import withplanner.domain.user.model.User;
import withplanner.domain.user.repository.UserRepository;
import withplanner.domain.community.model.Community;
import withplanner.global.dto.comment.CommentCreateReq;
import withplanner.global.dto.comment.CommentCreateRes;
import withplanner.global.exception.BaseException;

import static withplanner.global.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;


    public CommentCreateRes createComment(CommentCreateReq reqDto, Long userId, Long communityId, Long postId) {
        Comment comment;

        //user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        //community
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_COMMUNITY));

        comment = new Comment(reqDto.getComment());

        //users 연관관계 매핑
        comment.connectUser(user.getId()); // todo: 간접 참조 수정 필요

//        //커뮤니티가 mapPost 타입이면 mapPost 엔티티 조회
//        if(community.getType().equals(Type.mapPost)){
//            //mapPost
//            MapPost mapPost = mapPostRepository.findById(postId)
//                    .orElseThrow(()-> new BaseException(NOT_EXISTS_MAP_POST));
//            //mapPost와 연관관계 매핑
//            comment.connectMapPost(mapPost);
//
//        }
        //post 조회 후 연관관계 매핑
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_POST));
        comment.connectPost(post);

        //저장
        commentRepository.save(comment);

        return CommentCreateRes.toDto(comment, user.getNickname());
    }

}
