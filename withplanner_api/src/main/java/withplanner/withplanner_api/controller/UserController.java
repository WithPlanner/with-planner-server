package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.UserRequestDto;
import withplanner.withplanner_api.dto.login.LoginReq;
import withplanner.withplanner_api.dto.login.LoginRes;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.exception.BaseResponseStatus;
import withplanner.withplanner_api.jwt.JwtTokenProvider;
import withplanner.withplanner_api.repository.UserRepository;
import withplanner.withplanner_api.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     */
    @PostMapping("/sign_up/submit")
    @Transactional
    public Long join(@ModelAttribute UserRequestDto userRequestDto) {
        passwordEncoder.encode(userRequestDto.getPw());
        userRequestDto.encodePassword(passwordEncoder.encode(userRequestDto.getPw()));
        String role = "ROLE_USER";
        return userService.join(userRequestDto,role);
    }

    /**
     * 이메일 인증
     */


    /**
     * 이메일 중복 확인
     * @param email
     * @return 사용 불가능한 중복 이메일이면 -> true, 사용 가능한 이메일이면 -> false
     */
    @PostMapping("/sign_up/check_dup_email")
    public boolean checkDupEmail(@RequestParam String email) {
        return userService.checkDupEmail(email);
    }

    /**
     * 닉네임 중복 확인
     * @param nickname
     * @return 사용 불가능한 중복 닉네임이면 -> true, 사용 가능한 닉네임이면 -> false
     */
    @PostMapping("/sign_up/check_dup_nickname")
    public boolean checkDupNickname(@RequestParam String nickname) {
        return userService.checkDupNickname(nickname);
    }



    /**
     * 로그인
     * @Body email, password
     * @
     */
    @PostMapping("/login")
    @Transactional
    public LoginRes login(@RequestBody LoginReq loginReq){



        if(loginReq.getEmail()==null)
            throw new BaseException(BaseResponseStatus.EMPTY_EMAIL);
        if(loginReq.getPassword()==null)
            throw new BaseException(BaseResponseStatus.EMPTY_PASSWORD);
        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(()->new IllegalArgumentException("가입되지 않은 이메일 입니다. "));

        System.out.println("아아아아아아아아 "+loginReq.getEmail());
        System.out.println("아아아아아아아아 "+loginReq.getPassword());
        System.out.println("아아아아아아아아 "+user.getPwd());


        //if (!passwordEncoder.matches(loginReq.getPassword(), user.getPwd())) {
        //    throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        //}

        if(!loginReq.getPassword().equals(user.getPwd())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return new LoginRes(jwtTokenProvider.createToken(user.getId(),user.getRoles()));
    }

}
