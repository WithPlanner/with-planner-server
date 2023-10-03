package withplanner.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import withplanner.global.exception.BaseException;
import withplanner.global.exception.BaseResponseStatus;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            Long id = null;

            //token값이 null인지 확인합니다.
            if (token == null) {
                throw new BaseException(BaseResponseStatus.NOT_JWT_IN_HEADER);
            }

            // 유효한 토큰인지 확인합니다.
            if (jwtTokenProvider.validateToken(token)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                // 토큰으로부터 사용자의 id값을 받아옵니다.
                id = jwtTokenProvider.getUserPk(token);

                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }
        chain.doFilter(request, response);
    }

}
