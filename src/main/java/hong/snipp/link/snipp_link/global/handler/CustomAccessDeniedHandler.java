package hong.snipp.link.snipp_link.global.handler;

import hong.snipp.link.snipp_link.global.bean.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * packageName    : hong.snipp.link.snipp_link.global.handler
 * fileName       : CustomAccessDeniedHandler
 * author         : work
 * date           : 2025-04-18
 * description    : 403(Forbidden) 일 경우 실행
 *                  => 로그인을 통해 인증은 됐지만, 인가에서 걸린 경우
 *                  => 공통 에러 응답으로 내려주도록 변경
 *                  * 권한
 *                  * csrf
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException deniedException) throws IOException {
        String deniedMessage = deniedException.getMessage();
        if (deniedMessage != null) {
            String message = deniedMessage.toLowerCase();
            if (message.contains("csrf")) {
                log.warn("======================= CSRF 문제 발생 ===========================");
                this.returnMessage(res, "CSRF 토큰이 유효하지 않거나 누락되었습니다.", "CSRF Error");
            } else {
                log.warn("======================= 권한 부족 ===========================");
                this.returnMessage(res, "해당 리소스에 접근할 권한이 없습니다.", "Access Denied");
            }
        }
    }

    private void returnMessage(HttpServletResponse res, String message, String error) throws IOException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = res.getWriter();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, message, error);
        String jsonResponse = ErrorResponse.toJson(errorResponse);
        writer.write(jsonResponse);
        writer.flush();
    }
}
