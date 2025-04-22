package hong.snipp.link.snipp_link.global.interceptor;

import hong.snipp.link.snipp_link.global.auth.PrincipalDetails;
import hong.snipp.link.snipp_link.global.auth.dto.SnippSessionUser;
import hong.snipp.link.snipp_link.global.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * 2025-04-22        work       로직 구현
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        if(UserUtil.isAuthenticated(authentication)) {
            PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
            SnippSessionUser sessionUser = details.getUser();
            request.setAttribute(UserUtil.REQUEST_USER_KEY, sessionUser);
        }

        return true;
    }

}
