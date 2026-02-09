package hong.snipp.link.snipp_link.domain;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.domain
 * fileName       : HomeController
 * author         : work
 * date           : 2025-04-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 * 2025-04-21        work       회원가입 관련 url 추가
 * 2025-04-22        work       기본 메인 페이지 URL : /snipp
 * 2026-02-09        work       인증된 사용자는 로그인 페이지 접근시, /snipp 리디릭션
 */

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping({"/", "/snipp"})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        // 인증 정보가 있고, 실제 인증된 사용자(익명 사용자 ROLE_ANONYMOUS 제외)라면
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/snipp"; // 로그인한 유저는 메인으로 튕겨냄
        }
        return "login"; // 로그인 안 한 유저만 로그인 페이지 진입
    }

    // todo 추후 삭제
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    private String blockDirectAccess(HttpServletRequest req) {
        String referer = req.getHeader("referer");
        if (referer == null) return "redirect:/login";
        return null;
    }

    @GetMapping("/join1")
    public String register1(Model model, HttpServletRequest req) {
        String blocked = blockDirectAccess(req);
        return blocked != null ? blocked : "join/join1";
    }

    @GetMapping("/join2")
    public String register2(Model model, HttpServletRequest req) {
        String blocked = blockDirectAccess(req);
        return blocked != null ? blocked : "join/join2";
    }

    @GetMapping("/join3")
    public String register3(Model model, HttpServletRequest req) {
        String blocked = blockDirectAccess(req);
        return blocked != null ? blocked : "join/join3";
    }
}
