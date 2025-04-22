package hong.snipp.link.snipp_link.global.util;

import hong.snipp.link.snipp_link.global.auth.dto.SnippSessionUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * packageName    : hong.snipp.link.snipp_link.global.util
 * fileName       : UserUtil
 * author         : work
 * date           : 2025-04-22
 * description    : 유저 관련 유틸
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 */
public class UserUtil {

    public static final String REQUEST_USER_KEY = "user";

    public static SnippSessionUser getLoginUser() {
        return getUser(WebUtil.nowRequest());
    }

    public static Long getLoginUserUid() {
        SnippSessionUser user = getUser(WebUtil.nowRequest());
        return user.getUid();
    }

    public static SnippSessionUser getUser(HttpServletRequest request) {
        Object user = request.getAttribute(REQUEST_USER_KEY);
        return user != null ? (SnippSessionUser) user : null;
    }

    public static boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.getPrincipal() != "anonymousUser";
    }

}
