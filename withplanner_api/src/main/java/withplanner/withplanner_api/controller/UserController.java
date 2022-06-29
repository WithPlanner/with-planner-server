package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import withplanner.withplanner_api.dto.UserRequestDto;
import withplanner.withplanner_api.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/sign_up/submit")
    @Transactional
    public Long join(@ModelAttribute UserRequestDto userRequestDto) {
        return userService.join(userRequestDto);
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


}
