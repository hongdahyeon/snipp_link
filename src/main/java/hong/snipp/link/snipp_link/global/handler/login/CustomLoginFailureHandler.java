package hong.snipp.link.snipp_link.global.handler.login;

import hong.snipp.link.snipp_link.domain.enumcode.LoginTp;
import hong.snipp.link.snipp_link.domain.loginhist.dto.request.SnippLoginHistSave;
import hong.snipp.link.snipp_link.domain.loginhist.service.SnippLoginHistService;
import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.domain.user.service.SnippAuthUserService;
import hong.snipp.link.snipp_link.global.auth.oauth2.OAuth2ErrorCode;
import hong.snipp.link.snipp_link.global.auth.oauth2.OAuth2ErrorCustom;
import hong.snipp.link.snipp_link.global.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * packageName    : hong.snipp.link.snipp_link.global.handler.login
 * fileName       : CustomLoginFailureHandler
 * author         : work
 * date           : 2025-04-18
 * description    : 로그인 실패 핸들러
 *                  -> 폼 로그인
 *                  -> 소셜 로그인
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 * 2025-04-22        work       (1) 로그인 실패 이력 저장 + 이유
 *                              (2) 폼 로그인 실패 : {userEmail} 정보도 같이 전송
 * 2026-02-02        work       SessionAuthenticationException 추가
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private final SnippAuthUserService authUserService;
    private final SnippLoginHistService loginHistService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String userId = request.getParameter("userId");

        // { 폼 로그인 } 비밀번호 오류
        if( exception instanceof BadCredentialsException) {
            SnippUserView user = authUserService.findUserByUserId(userId);
            if( user != null ) {

                Integer pwdFailCnt = user.getPwdFailCnt() + 1;
                authUserService.changePwdFailCnt(userId, pwdFailCnt);
                String message = (pwdFailCnt == 5) ? FailureException.PASSWORD_FAIL_5_TIMES.message : FailureException.PASSWORD_FAIL.getMessage(pwdFailCnt);
                String type = (pwdFailCnt == 5) ? FailureException.PASSWORD_FAIL_5_TIMES.type : FailureException.PASSWORD_FAIL.type;
                this.saveLoginFailure(request, user.getUserEmail(), message);
                sendMssgAndRedirect(message, type, userId, user.getUserEmail(), response);

            } else {

                // 해당 사용자가 없음
                sendMssgAndRedirect(FailureException.UsernameNotFoundException.message, FailureException.UsernameNotFoundException.type, userId, "", response);
            }

        } else {

            SnippUserView user = authUserService.findUserByUserId(userId);

            /* [추가] 중복 로그인 발생 시 처리 */
            if (exception instanceof SessionAuthenticationException) {
                String message = FailureException.DuplicateLogin.message;
                request.getSession().setAttribute("userId", userId);    // 세션에 userId를 담아 forceLogin(강제 로그인) 시 사용할 수 있게 함
                sendMssgAndRedirect(message, FailureException.DuplicateLogin.type, userId, user.getUserEmail(), response);
            }

            /* [폼로그인] 계정 비활성화 : 관리자가 사용자의 계정 비활성화 */
            if(exception instanceof DisabledException) {
                String message = FailureException.DisabledException.message;
                this.saveLoginFailure(request, user.getUserEmail(), message);
                sendMssgAndRedirect(message, FailureException.DisabledException.type, userId, user.getUserEmail(), response);
            }

            /* [폼로그인] 비밀번호 만료 : 변경일로부터 90일 지남 */
            if(exception instanceof CredentialsExpiredException) {
                String message = FailureException.CredentialsExpiredException.message;
                this.saveLoginFailure(request, user.getUserEmail(), message);
                sendMssgAndRedirect(message, FailureException.CredentialsExpiredException.type, userId, user.getUserEmail(), response);
            }

            /* [폼로그인] 휴먼 계정 : 최근 로그인 시점이 1년 지남 */
            if(exception instanceof AccountExpiredException) {
                String message = FailureException.AccountExpiredException.message;
                this.saveLoginFailure(request, user.getUserEmail(), message);
                sendMssgAndRedirect(message, FailureException.AccountExpiredException.type, userId, user.getUserEmail(), response);
            }

            /* [폼로그인] 비밀번호 5회 오류로 계정 잠김 */
            if(exception instanceof LockedException) {
                String message = FailureException.LockedException.message;
                this.saveLoginFailure(request, user.getUserEmail(), message);
                sendMssgAndRedirect(message, FailureException.LockedException.type, userId, user.getUserEmail(), response);
            }

            /* [소셜 로그인] */
            if( exception instanceof OAuth2AuthenticationException ) {

                OAuth2ErrorCustom error = (OAuth2ErrorCustom) ((OAuth2AuthenticationException) exception).getError();
                OAuth2ErrorCode errorCode = error.getOAuth2ErrorCode();
                String userEmail = error.getUserEmail();
                String socialUserId = error.getUserId();

                /* [소셜 로그인] 이메일 중복 */
                if(OAuth2ErrorCode.socialEmailDuplicate == errorCode) {
                    sendMssgAndRedirectSocial(OAuth2ErrorCode.socialEmailDuplicate.message, OAuth2ErrorCode.socialEmailDuplicate.type, socialUserId, userEmail, request, response);
                }

                /* [소셜 로그인] 계정 비활성화  */
                if(OAuth2ErrorCode.socialEnable == errorCode) {
                    sendMssgAndRedirectSocial(OAuth2ErrorCode.socialEnable.message, OAuth2ErrorCode.socialEnable.type, socialUserId, userEmail, request, response);
                }

                /* [소셜 로그인] 계정 잠김 */
                if(OAuth2ErrorCode.socialLock == errorCode) {
                    sendMssgAndRedirectSocial(OAuth2ErrorCode.socialLock.message, OAuth2ErrorCode.socialLock.type, socialUserId, userEmail, request, response);
                }

                /* [소셜 로그인] 휴먼 계정 : 최근 로그인 시점이 1년 지남 */
                if(OAuth2ErrorCode.socialExpired == errorCode) {
                    sendMssgAndRedirectSocial(OAuth2ErrorCode.socialExpired.message, OAuth2ErrorCode.socialExpired.type, socialUserId, userEmail, request, response);
                }

                /* [소셜 로그인] 로그인 과정에서 오류 발생 */
                if(errorCode == null) {
                    sendMssgAndRedirectSocial(OAuth2ErrorCode.socialError.message, OAuth2ErrorCode.socialError.type, socialUserId, userEmail, request, response);
                }

            }
        }

        /* [폼로그인] 로그인 과정에서 오류 발생 */
        if(exception instanceof InternalAuthenticationServiceException) {
            sendMssgAndRedirect(FailureException.InternalAuthenticationServiceException.message, FailureException.InternalAuthenticationServiceException.type, userId, "", response);
        }
    }

    public void sendMssgAndRedirect(String message, String type, String userId, String userEmail, HttpServletResponse response) throws IOException {
        String sendMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect("/login?type="+type+"&userId="+userId+"&userEmail="+userEmail+"&mssg="+sendMessage);
    }

    public void sendMssgAndRedirectSocial(String message, String type, String userId, String userEmail, HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.saveLoginFailure(request, userEmail, message);
        String sendMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect("/login?type="+type+"&userId="+userId+"&userEmail="+userEmail+"&mssg="+sendMessage);
    }

    private void saveLoginFailure(HttpServletRequest request, String userEmail, String reason) {
        /* 로그인 이력 저장 */
        String loginIp = WebUtil.getIpAddress(request);
        String loginUserAgent = request.getHeader("User-Agent");
        SnippLoginHistSave loginHistBean = SnippLoginHistSave.insertLoginHist()
                .userEmail(userEmail)
                .accessIp(loginIp)
                .accessUserAgent(loginUserAgent)
                .loginAccessTp(LoginTp.LOGIN_FAIL.name())
                .loginAccessDescription(reason)
                .build();
        loginHistService.saveLoginHist(loginHistBean);
    }
}
