package hong.snipp.link.snipp_link.global.jwt;

import hong.snipp.link.snipp_link.global.config.Paths;
import hong.snipp.link.snipp_link.global.handler.login.FailureException;
import hong.snipp.link.snipp_link.global.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static hong.snipp.link.snipp_link.global.jwt.JwtProvider.*;


/**
 * packageName    : hong.snipp.link.snipp_link.global.jwt
 * fileName       : JwtAuthenticationFilter
 * author         : work
 * date           : 2026-02-05
 * description    : JWT 토큰 필터
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-05        work       최초 생성
 */


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;

    private String resolveToken(HttpServletRequest request) {
        // 1. 헤더 체크 (Ajax 요청용)
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        // 2. 쿠키 체크 (Thymeleaf 페이지 이동/새로고침용)
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (AccessTokenCookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        AntPathMatcher pathMatcher = new AntPathMatcher();

        for (String pattern : Paths.JWT_EXCLUDE_PATTERNS) {
            //log.info("==== JwtFilter >> path: {} =>> no check ", path);
            if (pathMatcher.match(pattern, path)) {
                return true; // 필터 안 탐 (통과)
            }
        }
        //log.info("==== JwtFilter >> path: {} =>> need check", path);
        return false; // 그 외(특히 /api/**)는 철저히 검사
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        // 1. 헤더에 있는 토큰 조회
        if (token != null && jwtProvider.isValidateToken(token)) {

            String userId = jwtProvider.getUserIdFromToken(token);                          // 2-1. 토큰에 담긴 {userId} 조회
            String savedToken = redisTemplate.opsForValue().get(RedisAccessKey + userId);   // 2-2. 장부(Redis)에 {userId}로 저장된 ACCESS_TOKEN 조회

            // 3. 장부(Redis)에 토큰이 없거나 or 헤더에 담긴 ACCESS_TOKEN 값과 장부(Redis)에 보관 중인 ACCESS_TOKEN 값이 다른지 체크
            // => 중복 로그인(밀어내기) 체크
            if (savedToken == null || !token.equals(savedToken)) {

                log.warn("========>>> JwtFilter:: 중복 로그인 혹은 만료된 장부: userId={}", userId);
                // 쿠키 삭제 및 에러 응답
                CookieUtil.clearCookie(response, AccessTokenCookieName);
                CookieUtil.clearCookie(response, RefreshTokenCookieName);

                // 2. 만약 API 요청(/api/**)인 경우와 일반 페이지 요청을 구분해서 응답하면 더 좋습니다.
                String path = request.getServletPath();
                if (path.startsWith("/api/")) {
                    // API 요청 >> 401 에러 반환 (프론트에서 처리할 수 있게)
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Multiple Login Detected");
                } else {
                    // 타임리프 페이지 요청 >> 로그인 페이지로 리다이렉트
                    String type = FailureException.SessionExpired.type;
                    String sendMessage = URLEncoder.encode(FailureException.SessionExpired.message, StandardCharsets.UTF_8);
                    response.sendRedirect("/login?type="+type+"&mssg="+sendMessage);
                }
                return; // 필터 체인 종료

            }
            log.info("========>>> JwtFilter >> ACCESS_TOKEN 일치 !!");
            Authentication auth = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        // 5. 다음 필터로 진행 (토큰이 없어도 permitAll 경로라면 페이지가 열림)
        log.info("========>>> JwtFilter:: NO Token ...");
        filterChain.doFilter(request, response);
    }
}
