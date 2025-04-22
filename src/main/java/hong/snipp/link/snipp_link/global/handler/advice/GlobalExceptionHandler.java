package hong.snipp.link.snipp_link.global.handler.advice;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        return "error/500";
    }
}
