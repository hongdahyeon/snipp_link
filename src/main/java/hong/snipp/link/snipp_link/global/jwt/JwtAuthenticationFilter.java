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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
 * 2026-02-06        work       요청 path에 따라 jwt 조회 구분
 *                              (1) /api/**  : ajax요청을 통해 들어오기에 무조건 헤더에서만 체크
 *                              (2) 화면 이동 : 쿠키+헤더에서 jwt 체크
 */


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public String resolveToken(HttpServletRequest req) {

        String path = req.getServletPath();
        String bearer = req.getHeader("Authorization");

        // 1. API 요청(/api/**)인 경우 -> 오직 헤더만 인정
        if (path.startsWith("/api/")) {
            if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
                log.info("===> API 요청, 헤더에 토큰 있음");
                return bearer.substring(7);
            }
            log.info("===> API 요청, 헤더에 토큰 없음");
            return null; // API인데 헤더에 토큰 없으면 바로 탈락
        }

        // 2. 일반 페이지 요청인 경우 -> 헤더 우선, 없으면 쿠키 체크 (화면 이동용)
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            log.info("===> 페이지 요청, 헤더에 토큰 있음");
            return bearer.substring(7);
        }
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (AccessTokenCookieName.equals(cookie.getName())) {
                    log.info("===> 페이지 요청, 쿠키에 토큰 있음");
                    return cookie.getValue();
                }
            }
        }
        log.info("===> 쿠키 없음");
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.resolveToken(request);
        String path = request.getServletPath();

        // 1. 헤더에 있는 토큰 조회
        if (token != null && jwtProvider.isValidateToken(token)) {

            String userId = jwtProvider.getUserIdFromToken(token);                          // 2-1. 토큰에 담긴 {userId} 조회
            String savedToken = redisTemplate.opsForValue().get(RedisAccessKey + userId);   // 2-2. 장부(Redis)에 {userId}로 저장된 ACCESS_TOKEN 조회

            // 3. 장부(Redis)에 토큰이 없거나 or 헤더에 담긴 ACCESS_TOKEN 값과 장부(Redis)에 보관 중인 ACCESS_TOKEN 값이 다른지 체크
            if( savedToken != null && token.equals(savedToken) ) {

                // 4-1. 인증 성공
                log.info("========>>> JwtFilter >> 인증 성공: userId={}", userId);
                Authentication auth = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);

            } else {

                // 4-2. 토큰은 있는데 장부와 다르거나 만료된 경우
                log.warn("========>>> JwtFilter:: 만료되거나 유효하지 않은 토큰. path: {}", path);

                // 단, 공개 경로인 경우에는 에러를 내지 않고 세션만 클리어하고 보내기
                if (isPublicPath(request)) {
                    log.info("========>>> JwtFilter:: It is Public path: {}", path);
                    clearCookie(response);
                } else {
                    // 보호된 경로인데 토큰이 이상하면 로그인 페이지로 리다이렉트
                    log.info("========>>> JwtFilter:: It is Private Path: {}", path);
                    handleInvalidToken(request, response);
                    return;
                }
            }
        } else {
            // 토큰 자체가 없는 경우
            if (token == null) {
                log.info("========>>> JwtFilter:: No Token Found for path: {}", path);
            }
        }
        // 5. 다음 필터로 진행 (토큰이 없어도 permitAll 경로라면 페이지가 열림)
        filterChain.doFilter(request, response);
    }

    // Paths 설정을 기반으로 공개 경로인지 확인하는 로직
    private boolean isPublicPath(HttpServletRequest request) {
        for (AntPathRequestMatcher matcher : Paths.BEFORE_LOGIN) {
            if (matcher.matches(request)) return true;
        }
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String pattern : Paths.JWT_EXCLUDE_PATTERNS) {
            if (pathMatcher.match(pattern, request.getServletPath())) return true;
        }
        return false;
    }

    private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        clearCookie(response);
        if (request.getServletPath().startsWith("/api/")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"UNAUTHORIZED\"}");
        } else {
            String type = FailureException.SessionExpired.type;
            String mssg = URLEncoder.encode(FailureException.SessionExpired.message, StandardCharsets.UTF_8);
            response.sendRedirect("/login?type=" + type + "&mssg=" + mssg);
        }
    }

    private void clearCookie(HttpServletResponse response) {
        CookieUtil.clearCookie(response, AccessTokenCookieName);
        CookieUtil.clearCookie(response, RefreshTokenCookieName);
    }
}
