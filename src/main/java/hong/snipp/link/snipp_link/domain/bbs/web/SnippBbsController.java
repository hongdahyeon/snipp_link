package hong.snipp.link.snipp_link.domain.bbs.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.web
 * fileName       : SnippBbsController
 * author         : work
 * date           : 2025-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/snipp/bbs")
public class SnippBbsController {

    @GetMapping("/super")
    public String index() {
        return "super/bbs/index";
    }
}
