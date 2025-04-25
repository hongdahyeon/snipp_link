package hong.snipp.link.snipp_link.domain.shorturl.log.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.log.web
 * fileName       : SnippSUrlLogController
 * author         : work
 * date           : 2025-04-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-25        work       최초 생성
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/snipp/short-url/access-log")
public class SnippSUrlLogController {

    @GetMapping("/super")
    public String index() {
        return "super/shorturl/log/index";
    }
}
