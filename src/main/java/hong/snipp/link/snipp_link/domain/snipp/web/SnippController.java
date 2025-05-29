package hong.snipp.link.snipp_link.domain.snipp.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.snipp.web
 * fileName       : SnippController
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
@RequestMapping("/snipp")
public class SnippController {

    @GetMapping("/introduce")
    public String introduce() {
        return "snipp/introduce";
    }
}