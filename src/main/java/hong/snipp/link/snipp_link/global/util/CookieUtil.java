package hong.snipp.link.snipp_link.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.WebUtils;


/**
 * packageName : hong.snipp.link.snipp_link.global.util
 * fileName : CookieUtil
 * author : work
 * date : 2026-02-04
 * description : 쿠키 유틸 클래스 추가
 * ===========================================================
 * DATE             AUTHOR      NOTE
 * -----------------------------------------------------------
 * 2026-02-04       work        최초 생성
 * 2026-02-05       work        clearCookie, getCookie 생성
 */

public class CookieUtil {

    /**
     * @method      createCookie
     * @author      dahyeon
     * @date        2026-02-04
     * @deacription 쿠키 생성
     *              :: maxAge = -1 :: 무제한
    **/
    public static void createCookie( HttpServletResponse res, String name, String value,
                                     int maxAge, boolean httpOnly, String path) {
        int expiryAge = (maxAge == -1) ? Integer.MAX_VALUE : maxAge;
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(expiryAge);
        cookie.setPath(path);
        res.addCookie(cookie);
    }

    /**
     * @method      clearCookie
     * @author      dahyeon
     * @date        2026-02-04
     * @deacription 쿠키 초기화: maxAge(0)
    **/
    public static void clearCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * @method      getCookie
     * @author      dahyeon
     * @date        2026-02-04
     * @deacription 쿠키 조회
    **/
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }
}
