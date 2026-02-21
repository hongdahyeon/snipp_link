package hong.snipp.link.snipp_link.domain.loginhist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.loginhist.controller
 * fileName       : SnippLoginHistController
 * author         : work
 * date           : 2025-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/snipp/login-hist")
public class SnippLoginHistController {

    @GetMapping("/super")
    public String index() {
        return "super/loginhist/index";
    }
}
