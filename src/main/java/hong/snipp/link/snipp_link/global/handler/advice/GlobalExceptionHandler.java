package hong.snipp.link.snipp_link.global.handler.advice;


import hong.snipp.link.snipp_link.global.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * packageName    : hong.snipp.link.snipp_link.global.handler.advice
 * fileName       : GlobalExceptionHandler
 * author         : work
 * date           : 2025-04-15
 * description    : ** 모든 컨트롤러에서 발생하는 예외를 공통적으로 처리
 *                  > Spring MVC에서 예외를 처리하거나, 모델 데이터를 처리할때 이용
 *                  > 웹 애플리케이션 화면을 랜더링하거나, 뷰를 반환
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-22        work       500 랜딩
 * 2025-04-25        work       {@RestControllerAdvice} 랑 엮이면서 생기는 문제로 인해 제거
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ModelAttribute("isLogin")
    public boolean isLogin(Authentication auth) {
        return auth != null && auth.isAuthenticated();
    }

    @ModelAttribute("isSuper")
    public boolean isSuper(Authentication auth) {
        if(auth != null && auth.isAuthenticated()) {
            String role = ((PrincipalDetails) auth.getPrincipal()).getUser().getRole();
            return "ROLE_SUPER".equals(role);
        }
        return false;
    }
}
