package hong.snipp.link.snipp_link.global.handler.login;

import hong.snipp.link.snipp_link.global.jwt.JwtProvider;
import hong.snipp.link.snipp_link.global.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static hong.snipp.link.snipp_link.global.jwt.JwtProvider.AccessTokenCookieName;

/**
 * packageName    : hong.snipp.link.snipp_link.global.handler.login
 * fileName       : CustomLogoutHandler
 * author         : work
 * date           : 2025-04-18
 * description    : 로그아웃 핸들러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-06        work       최초 생성:: 로그아웃 시점에, Redis&Cookie clear
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 1. 토큰 추출
        String token = this.resolveToken(request);

        // 2. Redis 장부 삭제
        if (token != null && jwtProvider.isValidateToken(token)) {
            String userId = jwtProvider.getUserIdFromToken(token);
            redisTemplate.delete(JwtProvider.RedisAccessKey + userId);
            redisTemplate.delete(JwtProvider.RedisRefreshKey + userId);
        }

        // 3. 쿠키 삭제
        CookieUtil.clearCookie(response, AccessTokenCookieName);
        CookieUtil.clearCookie(response, JwtProvider.RefreshTokenCookieName);

        log.info("========>>> Logout success: Redis & Cookies cleared");
        response.sendRedirect("/login");
    }

    // JwtProvider 또는 필터 내부 로직 예시
    private String resolveToken(HttpServletRequest request) {
        // 1. 헤더 체크 (Ajax 요청일 때 주로 작동)
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        // 2. 쿠키 체크 (form submit 또는 페이지 이동 시 작동)
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (AccessTokenCookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
