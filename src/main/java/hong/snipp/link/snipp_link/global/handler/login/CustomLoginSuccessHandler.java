package hong.snipp.link.snipp_link.global.handler.login;

import hong.snipp.link.snipp_link.domain.code.LoginTp;
import hong.snipp.link.snipp_link.domain.loginhist.dto.request.SnippLoginHistSave;
import hong.snipp.link.snipp_link.domain.loginhist.service.SnippLoginHistService;
import hong.snipp.link.snipp_link.domain.user.service.SnippAuthUserService;
import hong.snipp.link.snipp_link.global.auth.PrincipalDetails;
import hong.snipp.link.snipp_link.global.auth.dto.SnippSessionUser;
import hong.snipp.link.snipp_link.global.jwt.JwtProvider;
import hong.snipp.link.snipp_link.global.util.CookieUtil;
import hong.snipp.link.snipp_link.global.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static hong.snipp.link.snipp_link.global.jwt.JwtProvider.*;

/**
 * packageName    : hong.snipp.link.snipp_link.global.handler.login
 * fileName       : CustomLoginSuccessHandler
 * author         : work
 * date           : 2025-04-18
 * description    : 로그인 성공 핸들러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 * 2025-04-22        work       로그인 성공 이력 저장 + 사유
 * 2025-04-22        work       로그인 성공시 랜딩 페이지 : /snipp
 * 2026-02-05        work       JWT 사용 추가 관련 로직 개발
 * 2026-02-06        work       소셜 로그인 > userId 가져오기 (userId null로 인한 JWT 생성시 오류 발생)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final SnippAuthUserService authUserService;
    private final SnippLoginHistService loginHistService;

    // {{ JWT 사용 추가
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    // }}

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String userEmail = "";
        String userId = request.getParameter("userId");
        if(userId == null) {

            // JWT 토큰 발급을 위해 userId 가져오기
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            userId = principalDetails.getUser().getUserId();

            /* oauth2 login */
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> map = new HashMap<>();

            map = oAuth2User.getAttribute("response");
            if(map == null) {
                map = oAuth2User.getAttribute("kakao_account");
                if(map == null) userEmail = oAuth2User.getAttribute("email");
                else userEmail = (String) map.get("email");
            } else userEmail = (String) map.get("email");

            log.info("======================= Login User: [OAuth2 Login][userId: {}][email: {}] ===========================", userId, userEmail);
            authUserService.resetLastLoginDtAndPwdFailCntByUserEmail(userEmail);

        } else {

            /* form login */
            SnippSessionUser sessionUser = ((PrincipalDetails) authentication.getPrincipal()).getUser();
            String role = sessionUser.getRole();
            log.info("======================= Login User: [Role: {}][userId: {}][email: {}] ===========================", role, userId, userEmail);
            authUserService.resetLastLoginDtAndPwdFailCntByUserId(userId);
            userEmail = sessionUser.getUserEmail();
        }

        // {{ JWT 사용 추가 :  JWT 발급 및 Redis 장부 기입
        this.customForJWT(userId, userEmail, response);
        // }}

        /* 로그인 이력 저장 */
        this.saveUserLoginHist(request, userEmail);

        String landingPage = "/snipp";
        response.sendRedirect(landingPage);
    }
    
    /**
     * @method      saveUserLoginHist
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription 로그인 이력 저장
    **/
    private void saveUserLoginHist(HttpServletRequest req, String userEmail) {
        String loginIp = WebUtil.getIpAddress(req);
        String loginUserAgent = req.getHeader("User-Agent");
        SnippLoginHistSave loginHistBean = SnippLoginHistSave.insertLoginHist()
                .userEmail(userEmail)
                .accessIp(loginIp)
                .accessUserAgent(loginUserAgent)
                .loginAccessTp(LoginTp.LOGIN_SUCCESS.name())
                .loginAccessDescription(LoginTp.LOGIN_SUCCESS.name())
                .build();
        loginHistService.saveLoginHist(loginHistBean);
    }

    /**
     * @method      customForJWT
     * @author      dahyeon
     * @date        2026-02-05
     * @deacription JWT 토큰 발급 및 Redis(장부), 쿠키에 저장
    **/
    private void customForJWT(String userId, String userEmail, HttpServletResponse res) {
        // 1. 토큰 발급
        String accessToken = jwtProvider.createAccessToken(userId, userEmail);
        String refreshToken = jwtProvider.createRefreshToken(userId);

        // 2. Redis(장부)에 저장/업데이트
        redisTemplate.opsForValue().set(RedisAccessKey + userId, accessToken, jwtProvider.getAccessTokenTime(), TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(RedisRefreshKey + userId, refreshToken, jwtProvider.getRefreshTokenTime(), TimeUnit.MILLISECONDS);

        // 3. 헤더에 추가
        res.setHeader("Authorization", "Bearer " + accessToken);
        
        // 4. 쿠키에 토큰 저장
        CookieUtil.createCookie(res, AccessTokenCookieName, accessToken, (int)(jwtProvider.getAccessTokenTime() / 1000), false, "/");
        CookieUtil.createCookie(res, RefreshTokenCookieName, refreshToken, (int)(jwtProvider.getRefreshTokenTime() / 1000), true, "/");
        log.info("==== success >> create ACCESS, REFRESH TOKEN");
    }

}
