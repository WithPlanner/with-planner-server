package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.domain.EmailAuth;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.ResultLongResp;
import withplanner.withplanner_api.dto.ResultMsgResp;
import withplanner.withplanner_api.dto.UserRequestDto;
import withplanner.withplanner_api.dto.join.AuthNumberRes;
import withplanner.withplanner_api.dto.join.EmailAuthRes;
import withplanner.withplanner_api.dto.post.ListCardResp;
import withplanner.withplanner_api.dto.user.MyPageResp;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.repository.EmailRepository;
import withplanner.withplanner_api.repository.UserRepository;
import withplanner.withplanner_api.util.AuthEmailSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static withplanner.withplanner_api.exception.BaseResponseStatus.NOT_EXISTS_PARTICIPANT;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthEmailSender authMailSender;
    private final EmailRepository emailRepository;

    public ResultLongResp join(UserRequestDto userRequestDto, String role) {
        if (userRepository.existsByNickname(userRequestDto.getNickname()))
            return new ResultLongResp(-1L, "회원가입에 실패하였습니다.");

        User savedUser = userRepository.save(new User(userRequestDto,role));
        return new ResultLongResp(savedUser.getId(), "회원가입에 성공하였습니다.");
    }

    public boolean checkDupEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public ResultMsgResp checkDupNickname(String nickname) {
        if(userRepository.existsByNickname(nickname))
            return new ResultMsgResp("중복된 닉네임입니다.", false);
        return new ResultMsgResp("사용가능한 닉네임입니다.", true);
    }

    public EmailAuthRes checkValidEmail(String email) {
        if(checkDupEmail(email))
            return new EmailAuthRes(false);

        String[] domain = email.split("\\.|@");
        System.out.println("domain[1] = " + domain[1]);
        if(!domain[1].equals("sungshin"))
            return new EmailAuthRes(false);
        
        sendAuthEmail(email);
        return new EmailAuthRes(true);
    }

    public void sendAuthEmail(String email) {
        int authNumber = (int)(Math.random() * (99999 - 10000 + 1)) + 10000;
        emailRepository.save(
                EmailAuth.builder()
                        .email(email)
                        .authNumber(authNumber)
                        .expired(false)
                        .build()
        );
        authMailSender.sendMail(email, authNumber);
    }

    public AuthNumberRes confirmEmail(String email, int authNumber) {
        EmailAuth emailAuth = emailRepository.findByEmail(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지않는 이메일 입니다.")
                );
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(emailAuth.getExpireDate())) {
            if (emailAuth.getAuthNumber() == authNumber) {
                return new AuthNumberRes(true);
            }
            return new AuthNumberRes(false, "기한이 만료된 번호입니다.");
        }

        return new AuthNumberRes(false, "인증번호가 틀렸습니다.");
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다. "));
    }

    //UserService 구현체 관련 코드 - 필수
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByEmail(username)
               .orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public MyPageResp myPageListing(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));
        List<ListCardResp> commmunities = user.getCommunities().stream().map(
                c -> ListCardResp.builder()
                        .communityId(c.getId())
                        .name(c.getName())
                        .communityImg(c.getCommunityImg())
                        .type(c.getType().toString())
                        .category(c.getCategory().toString())
                        .build()
        ).collect(Collectors.toList());
        return MyPageResp.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .communities(commmunities)
                .build();
    }
}
