package hong.snipp.link.snipp_link.domain;

import org.springframework.stereotype.Controller;
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
 */

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
