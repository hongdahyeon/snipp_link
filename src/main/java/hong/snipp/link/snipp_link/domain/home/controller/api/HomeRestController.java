package hong.snipp.link.snipp_link.domain.home.controller.api;

import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.domain.user.service.SnippAuthUserService;
import hong.snipp.link.snipp_link.domain.user.service.SnippUserService;
import hong.snipp.link.snipp_link.global.jwt.JwtProvider;
import hong.snipp.link.snipp_link.global.session.SessionHelper;
import hong.snipp.link.snipp_link.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static hong.snipp.link.snipp_link.global.jwt.JwtProvider.RedisAccessKey;
import static hong.snipp.link.snipp_link.global.jwt.JwtProvider.RefreshTokenCookieName;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.home.controller.api
 * fileName       : HomeRestController
 * author         : work
 * date           : 2025-04-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-25        work       최초 생성
 * 2025-02-04        work       force.login
 * 2026-02-05        work       JWT 위한 REFRESH 토큰 발급 로직 추가
 * 2026-02-22        home       패키지 구조 변경
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeRestController {

    private final SnippAuthUserService authUserService;
    private final SessionHelper sessionHelper;

    // {{ JWT 사용 추가
    private final SnippUserService userService;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    // }}

    @GetMapping("/csrf")
    public Map<String, String> getCSRFToken(HttpServletRequest req) {
        CsrfToken csrf = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
        Map<String, String> response = new HashMap<>();
        response.put("token", csrf.getToken());
        return response;
    }

// {{ JWT 사용 추가 : 주석
//    @PostMapping("/login/force.json")
//    public ResponseEntity forceLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
//        HttpSession session = request.getSession();
//        String userId = (String) session.getAttribute("userId");
//
//        SnippUserView user = authUserService.findUserByUserId(userId);
//
//         SnippSessionUser sessionUser = new SnippSessionUser(user);
//         session.removeAttribute(userId);
//         sessionHelper.findSessionAndExpire(sessionUser, response);
//         Authentication authentication = sessionHelper.initializeAuthentication(sessionUser);
//         sessionHelper.createSessionAndEditSecurityContext(authentication, request);
//         log.info("======================= Force Login User: {} [Role: {}] ===========================", userId,  sessionUser.getRole());
//         response.sendRedirect("/snipp");
//         return ResponseEntity.ok().build();
//    }
// }}

    // {{ JWT 사용 추가
    @PostMapping("/api/auth/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest req) {
        log.info("==== home >> auth refresh start ...");
        // 1. 쿠키에 넣어둔 REFRESH_TOKEN 조회
        String refreshToken = CookieUtil.getCookie(req, RefreshTokenCookieName);

        // 2. {REFRESH_TOKEN}이 있고, 유효한 토큰이라면 ...
        if (refreshToken != null && jwtProvider.isValidateToken(refreshToken)) {

            String userId = jwtProvider.getUserIdFromToken(refreshToken);               // 3-1. REFRESH_TOKEN 안에 있는 {userId} 가져오기
            String savedRefresh = redisTemplate.opsForValue().get("refresh:" + userId); // 3-2. 장부(Redis)에서 REFRESH_TOKEN 가져오기

            // 4. 장부(Redis)에서 가져온 REFRESH_TOKEN * 쿠키에 넣어둔 REFRESH_TOKEN 일치 체크
            if (refreshToken.equals(savedRefresh)) {

                log.info("==== home >> REFRESH token Equals and make new ACCESS TOKEN ...");
                // 5-1. 일치한다면.. 새롭게 ACCESS_TOKEN 만들기
                // -> 장부(Redis): 새로운 ACCESS_TOKEN 덮어쓰기
                // -> 헤더: 새로운 ACCESS_TOKEN 덮어쓰기
                SnippUserView user = userService.findUserByUserId(userId);
                String userEmail = user.getUserEmail();
                String newAccessToken = jwtProvider.createAccessToken(userId, userEmail);
                redisTemplate.opsForValue().set(RedisAccessKey + userId, newAccessToken, jwtProvider.getAccessTokenTime(), TimeUnit.MILLISECONDS);
                return ResponseEntity.ok().header("Authorization", "Bearer " + newAccessToken).build();
            }
        }
        return ResponseEntity.status(401).build();
    }
    // }}
}
