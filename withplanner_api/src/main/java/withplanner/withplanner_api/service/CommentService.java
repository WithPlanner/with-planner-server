package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import withplanner.withplanner_api.domain.*;
import withplanner.withplanner_api.dto.comment.CommentCreateReq;
import withplanner.withplanner_api.dto.comment.CommentCreateRes;
import withplanner.withplanner_api.dto.community.CommunityCreateLocationRes;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.repository.*;

import static withplanner.withplanner_api.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final MapPostRepository mapPostRepository;
    private final PostRepository postRepository;


    public CommentCreateRes createComment(CommentCreateReq reqDto, Long userId,Long communityId, Long postId){
        Comment comment;

        //user
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(NOT_EXISTS_PARTICIPANT));

        //community
        Community community = communityRepository.findById(communityId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_COMMUNITY));

        comment = new Comment(reqDto.getComment());

        //users 연관관계 매핑
        comment.connectUser(user);

        //커뮤니티가 mapPost 타입이면 mapPost 엔티티 조회
        if(community.getType().equals(Type.mapPost)){
            //mapPost
            MapPost mapPost = mapPostRepository.findById(postId)
                    .orElseThrow(()-> new BaseException(NOT_EXISTS_MAP_POST));
            //mapPost와 연관관계 매핑
            comment.connectMapPost(mapPost);

        }
        //커뮤니티가 post 타입이면 post 엔티티 조회
        else{
            Post post = postRepository.findById(postId)
                    .orElseThrow(()-> new BaseException(NOT_EXISTS_POST));
            //post와 연관관계 매핑
            comment.connectPost(post);
            }

        //저장
        commentRepository.save(comment);

        return CommentCreateRes.toDto(comment, user.getNickname());}

}
