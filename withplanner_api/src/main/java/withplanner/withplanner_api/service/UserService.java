package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.UserRequestDto;
import withplanner.withplanner_api.dto.login.LoginReq;
import withplanner.withplanner_api.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Long join(UserRequestDto userRequestDto, String role) {
        User savedUser = userRepository.save(new User(userRequestDto,role));
        return savedUser.getId();
    }

    public boolean checkDupEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkDupNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
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
}
