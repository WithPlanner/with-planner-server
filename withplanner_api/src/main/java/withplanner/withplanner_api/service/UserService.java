package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.UserRequestDto;
import withplanner.withplanner_api.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public Long join(UserRequestDto userRequestDto) {
        userRequestDto.encodePassword(passwordEncoder.encode(userRequestDto.getPw()));
        User savedUser = userRepository.save(new User(userRequestDto));
        return savedUser.getId();
    }

    public boolean checkDupNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
