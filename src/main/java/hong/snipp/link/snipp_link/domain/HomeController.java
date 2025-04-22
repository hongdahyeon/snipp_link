package hong.snipp.link.snipp_link.domain;

import jakarta.servlet.http.HttpServletRequest;
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
 */

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping({"/", "/snipp"})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
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
