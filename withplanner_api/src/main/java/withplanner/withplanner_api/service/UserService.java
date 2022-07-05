package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.EmailAuth;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.UserRequestDto;
import withplanner.withplanner_api.repository.EmailRepository;
import withplanner.withplanner_api.repository.UserRepository;
import withplanner.withplanner_api.util.AuthEmailSender;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthEmailSender authMailSender;
    private final EmailRepository emailRepository;

    @Transactional
    public Long join(UserRequestDto userRequestDto) {
        System.out.println("userRequestDto = " + userRequestDto);
        if (checkDupNickname(userRequestDto.getNickname())) // 중복 닉네임이면 -1 반환
            return -1L;
        userRequestDto.encodePassword(passwordEncoder.encode(userRequestDto.getPw()));
        User savedUser = userRepository.save(new User(userRequestDto));
        return savedUser.getId();
    }

    public boolean checkDupEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkDupNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean checkValidEmail(String email) {
        if(checkDupEmail(email))
            return false;

        String[] domain = email.split("\\.|@");
        System.out.println("domain[1] = " + domain[1]);
        if(!domain[1].equals("sungshin"))
            return false;
        
        sendAuthEmail(email);
        return true;
    }

    @Transactional
    public void sendAuthEmail(String email) {
        String authToken = UUID.randomUUID().toString();
        emailRepository.save(
                EmailAuth.builder()
                        .email(email)
                        .authToken(authToken)
                        .expired(false)
                        .build()
        );
        authMailSender.sendMail(email, authToken);
    }

    public boolean confirmEmail(String email, String authToken) {
        EmailAuth emailAuth = emailRepository.findByEmail(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지않는 이메일 입니다.")
                );
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(emailAuth.getExpireDate())) {
            if (emailAuth.getAuthToken().equals(authToken))
                return true;
        }
        return false;
    }
}
