package withplanner.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import withplanner.global.config.Constants;
import withplanner.global.jwt.JwtAuthenticationFilter;
import withplanner.global.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//            throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    //로그인,회원가입에 대한 요청은 인증(권한) 없어도 접근 가능하도록 설정.
    //나머지 url은 무조건 인증을 받아야 함.
    //UsernamePasswordAuthenticationFilter전에 JwtAuthenticationFilter가 인터셉트 해서 토큰 유효성 검사.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtTokenValidationFilter = new JwtAuthenticationFilter(jwtTokenProvider);

        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET, Constants.permitAllArray).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenValidationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
