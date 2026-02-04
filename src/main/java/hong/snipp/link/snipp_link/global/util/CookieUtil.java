package hong.snipp.link.snipp_link.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


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
}
