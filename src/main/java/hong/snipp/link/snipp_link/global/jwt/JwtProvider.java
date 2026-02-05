package hong.snipp.link.snipp_link.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * packageName    : hong.snipp.link.snipp_link.global.jwt
 * fileName       : JwtProvider
 * author         : work
 * date           : 2026-02-05
 * description    : JWT 토큰 관련 Provider
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-05        work       최초 생성
 */

@Slf4j
@Component
public class JwtProvider {

    public static String RedisAccessKey = "auth:access:";
    public static String RedisRefreshKey = "auth:refresh:";
    public static String RefreshTokenCookieName = "refreshTokenCookie";
    public static String AccessTokenCookieName = "accessTokenCookie";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-time}")
    private long accessTokenTime;

    @Value("${jwt.refresh-time}")
    private long refreshTokenTime;

    private Key key;

    private final UserDetailsService userDetailsService;

    public JwtProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @method      createToken
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription 토큰 생성
    **/
    private String createToken(String userId, String userEmail, long expireTime) {
        Claims claims = Jwts.claims().setSubject(userId);
        if(userEmail != null) claims.put("userEmail", userEmail);
        Date now = new Date();
        Date expiryTime = new Date(now.getTime() + expireTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * @method      createAccessToken
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription ACCESS 토큰 생성
    **/
    public String createAccessToken(String userId, String userEmail) { return createToken(userId, userEmail, accessTokenTime); }

    /**
     * @method      createRefreshToken
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription REFRESH 토큰 생성
    **/
    public String createRefreshToken(String userId) { return createToken(userId, null, refreshTokenTime); }

    /**
     * @method      isValidateToken
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription TOKEN 유효성 체크
    **/
    public boolean isValidateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /**
     * @method      getUserIdFromToken
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription 토큰에 담긴 {userId} 조회
    **/
    public String getUserIdFromToken(String token) {
        try {
            // 정상적인 경우
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            // [핵심] 토큰이 만료되었을 때 예외(e) 내부에서 Claims
            log.info("만료된 토큰에서 userId 추출을 시도합니다.");
            return e.getClaims().getSubject();
        } catch (Exception e) {
            // 서명 위조나 잘못된 형식 등 다른 오류일 경우
            log.error("토큰 파싱 중 오류 발생: {}", e.getMessage());
            return null;
        }
    }

    /**
     * @method      getAuthentication
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription {token} 값을 통해 -> {userId} 조회 -> 유저 authentication 조회
    **/
    public Authentication getAuthentication(String token) {
        String userId = this.getUserIdFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * @method      getAccessTokenTime
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription ACCESS_TOKEN 만료 시간 조회
    **/
    public long getAccessTokenTime() {
        return accessTokenTime;
    }

    /**
     * @method      getRefreshTokenTime
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription REFRESH_TOKEN 만료 시간 조회
    **/
    public long getRefreshTokenTime() {
        return refreshTokenTime;
    }
}
