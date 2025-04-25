package hong.snipp.link.snipp_link.domain;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain
 * fileName       : HomeRestController
 * author         : work
 * date           : 2025-04-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-25        work       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class HomeRestController {

    @GetMapping("/csrf")
    public Map<String, String> getCSRFToken(HttpServletRequest req) {
        CsrfToken csrf = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
        Map<String, String> response = new HashMap<>();
        response.put("token", csrf.getToken());
        return response;
    }
}
