package withplanner.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import withplanner.domain.user.model.User;

import withplanner.global.dto.ResultLongResp;
import withplanner.global.dto.ResultMsgResp;
import withplanner.global.dto.UserRequestDto;
import withplanner.global.dto.join.AuthNumberRes;
import withplanner.global.dto.join.EmailAuthRes;
import withplanner.global.dto.login.LoginReq;
import withplanner.global.dto.login.LoginRes;
import withplanner.global.exception.BaseException;
import withplanner.global.exception.BaseResponse;
import withplanner.global.exception.BaseResponseStatus;
import withplanner.global.jwt.JwtTokenProvider;
import withplanner.domain.user.repository.UserRepository;
import withplanner.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입 + 닉네임 중복확인
     */
    @PostMapping("/sign_up/submit")
    public BaseResponse<ResultLongResp> join(@RequestBody UserRequestDto userRequestDto) {
        passwordEncoder.encode(userRequestDto.getPw());
        userRequestDto.encodePassword(passwordEncoder.encode(userRequestDto.getPw()));
        String role = "ROLE_USER";
        ResultLongResp result = userService.join(userRequestDto, role);
        return new BaseResponse<>(result);
    }

    /**
     * 이메일 인증 + 이메일 중복확인
     */
    @PostMapping("/sign_up/check_valid_email")
    public EmailAuthRes checkValidEmail(@RequestParam String email) {
        return userService.checkValidEmail(email);
    }

    @GetMapping("/sign_up/check_valid_email")
    public AuthNumberRes confirmEmail(@RequestParam String email, @RequestParam int authNumber) {
        return userService.confirmEmail(email, authNumber);
    }

    /**
     * 닉네임 중복 확인
     * @param nickname
     * @return 사용 불가능한 중복 닉네임이면 -> true, 사용 가능한 닉네임이면 -> false
     */
    @PostMapping("/sign_up/check_dup_nickname")
    public ResultMsgResp checkDupNickname(@RequestParam String nickname) {
        return userService.checkDupNickname(nickname);
    }


    /**
     * 로그인
     * @Body email, password
     * @
     */
    @PostMapping("/login")
    @Transactional
    public BaseResponse<LoginRes> login(@RequestBody LoginReq loginReq){
        if(loginReq.getEmail()==null)
            throw new BaseException(BaseResponseStatus.EMPTY_EMAIL);
        if(loginReq.getPassword()==null)
            throw new BaseException(BaseResponseStatus.EMPTY_PASSWORD);

        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(()->new BaseException(BaseResponseStatus.NOT_EXISTS_EMAIL));

        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPwd())) {
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_PASSWORD);
        }

//        if(!loginReq.getPassword().equals(user.getPwd())){
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
        LoginRes loginRes = new LoginRes(jwtTokenProvider.createToken(user.getId(),user.getRoles()));

        return new BaseResponse<>(loginRes);
    }

    /**
     * 자동 로그인 api
     */
    @PostMapping("/auto-login")
    @Transactional
    public BaseResponse<String> autoLogin(HttpServletRequest request){
        String message = "";

        String jwtToken = jwtTokenProvider.resolveToken(request);
        if(jwtTokenProvider.validateToken(jwtToken)){
            message = "자동로그인에 성공했습니다. ";
        }
        return new BaseResponse<>(message);
    }


//    @GetMapping("/mypage")
//    public BaseResponse<MyPageResp> myPageLisitng(@AuthenticationPrincipal User user) {
//        MyPageResp result = userService.myPageListing(user.getEmail());
//        return new BaseResponse<>(result);
//    }

}
