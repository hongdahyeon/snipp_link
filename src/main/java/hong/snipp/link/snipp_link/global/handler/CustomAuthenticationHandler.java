package hong.snipp.link.snipp_link.global.handler;

import hong.snipp.link.snipp_link.global.bean.error.ErrorResponse;
import hong.snipp.link.snipp_link.global.config.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : hong.snipp.link.snipp_link.global.handler
 * fileName       : CustomAuthenticationHandler
 * author         : work
 * date           : 2025-04-18
 * description    : 인증되지 않은 사용자(로그인하지 않은 사용자) 접근의 경우 타게 된다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 * 2026-02-04        work       [X-Requested-With] 헤더 없는 경우 대비
 */
@Component
public class CustomAuthenticationHandler extends LoginUrlAuthenticationEntryPoint {

    public CustomAuthenticationHandler() {
        super(Paths.LOGIN);
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException)
            throws IOException, ServletException {

        /*
        * 1. 헤더 추출
        * - 최근의 웹 개발 환경(ex. fetch API)에서는 'x-Requested-With' 헤더가 없는 경우 있음
        * -> 'Accept' 헤더 같이 확인
        * */
        String ajaxHeader = req.getHeader("X-Requested-With");
        String acceptHeader = req.getHeader("Accept");
        String authHeader = req.getHeader("Authorization"); // JWT 존재 여부 확인용

        // 2. AJAX 요청이거나, JSON 응답을 기대하거나, Bearer 토큰을 들고 온 경우
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        boolean isJsonExpected = acceptHeader != null && acceptHeader.contains("application/json");
        boolean hasJwt = authHeader != null && authHeader.startsWith("Bearer ");

        if (isAjax || isJsonExpected || hasJwt) {

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = res.getWriter();
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "인증이 필요합니다.", "Unauthorized");
            String jsonResponse = ErrorResponse.toJson(errorResponse);
            writer.write(jsonResponse);
            writer.flush();

        } else {

            // 기본 동작 (로그인 페이지로 리디렉션)
            super.commence(req, res, authException);
        }
    }
}
