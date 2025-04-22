package hong.snipp.link.snipp_link.global.handler.login;

import hong.snipp.link.snipp_link.domain.code.LoginTp;
import hong.snipp.link.snipp_link.domain.loginhist.dto.request.SnippLoginHistSave;
import hong.snipp.link.snipp_link.domain.loginhist.service.SnippLoginHistService;
import hong.snipp.link.snipp_link.domain.user.service.SnippAuthUserService;
import hong.snipp.link.snipp_link.global.auth.PrincipalDetails;
import hong.snipp.link.snipp_link.global.auth.dto.SnippSessionUser;
import hong.snipp.link.snipp_link.global.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final SnippAuthUserService authUserService;
    private final SnippLoginHistService loginHistService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String userEmail = "";
        String userId = request.getParameter("userId");
        if(userId == null) {

            /* oauth2 login */
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> map = new HashMap<>();

            map = oAuth2User.getAttribute("response");
            if(map == null) {
                map = oAuth2User.getAttribute("kakao_account");
                if(map == null) userEmail = oAuth2User.getAttribute("email");
                else userEmail = (String) map.get("email");
            } else userEmail = (String) map.get("email");

            log.info("======================= Login User: {} [OAuth2 Login] ===========================", userEmail);
            authUserService.resetLastLoginDtAndPwdFailCntByUserEmail(userEmail);

        } else {

            /* form login */
            SnippSessionUser sessionUser = ((PrincipalDetails) authentication.getPrincipal()).getUser();
            String role = sessionUser.getUserRole();
            log.info("======================= Login User: {} [Role: {}] ===========================", userId,  role);
            authUserService.resetLastLoginDtAndPwdFailCntByUserId(userId);
            userEmail = sessionUser.getUserEmail();
        }

        /* 로그인 이력 저장 */
        String loginIp = WebUtil.getIpAddress(request);
        String loginUserAgent = request.getHeader("User-Agent");
        SnippLoginHistSave loginHistBean = SnippLoginHistSave.insertLoginHist()
                .userEmail(userEmail)
                .accessIp(loginIp)
                .accessUserAgent(loginUserAgent)
                .loginAccessTp(LoginTp.LOGIN_SUCCESS.name())
                .loginAccessDescription(LoginTp.LOGIN_SUCCESS.name())
                .build();
        loginHistService.saveLoginHist(loginHistBean);

        String landingPage = "/";
        response.sendRedirect(landingPage);
    }

}
