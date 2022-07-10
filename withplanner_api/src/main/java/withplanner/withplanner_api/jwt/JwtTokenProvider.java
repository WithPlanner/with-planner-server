package withplanner.withplanner_api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.service.UserService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static withplanner.withplanner_api.exception.BaseResponseStatus.INVALID_JWT;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${secret.jwt_secret_key}")
    private String secretKey;

    //토큰유효시간 - 1년
    private long tokenValidTime =60*1000L*60*24*365;

    private final UserService userService;

    //secretkey를 base64로 인코딩
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    //jwt 토큰 생성
    public String createToken(Long userId,List<String> roles){
        Claims claims= Jwts.claims();//payload에 저장되는 정보단위
        claims.put("userId",userId);
        claims.put("roles",roles);
        Date now=new Date();
        return Jwts.builder()
                .setClaims(claims) //정보(userId) 저장
                .setIssuedAt(now) //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime()+tokenValidTime)) //만료 시간 정보
                .signWith(SignatureAlgorithm.HS256,secretKey) //사용할 암호화 알고리즘
                .compact();
    }

    //jwt토큰에서 얻은 userId로 인증정보 조회
    public Authentication getAuthentication(String token){
        User user =userService.findUserById(this.getUserPk(token)); //?
        return new UsernamePasswordAuthenticationToken(user,"",user.getAuthorities());
    }

    //토큰에서 userId 추출.
    public Long getUserPk(String token){
        Claims body;
        try{
            body = Jwts.parser().
                    setSigningKey(secretKey).
                    parseClaimsJws(token).
                    getBody();
         }
        catch(Exception exception){
            throw new BaseException(INVALID_JWT);
        }
        Long id = body.get("userId",Long.class);
        return id;

    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
