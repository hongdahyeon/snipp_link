package hong.snipp.link.snipp_link.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * packageName    : hong.snipp.link.snipp_link.global.interceptor
 * fileName       : UserInterceptor
 * author         : work
 * date           : 2025-04-15
 * description    : 유저 인터셉터
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

         // TODO : 로그인 구현 이후 작업 진행 //
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        if(UserUtil.isAuthenticated(authentication)) {
            PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
            details.getUser();
            request.setAttribute(UserUtil.REQUEST_USER_KEY, loginUser);
        }*/

        return true;
    }

}
