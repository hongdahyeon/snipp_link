package hong.snipp.link.snipp_link.domain.shorturl.shorturl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.shorturl.controller
 * fileName       : SnippShortUrlController
 * author         : work
 * date           : 2025-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 * 2025-04-25        work       index 리턴 html 경로 변경
 * 2026-02-22        home       패키지 구조 변경
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/snipp/short-url")
public class SnippShortUrlController {

    @GetMapping("/super")
    public String index() {
        return "super/shorturl/shorturl/index";
    }

    @GetMapping("/my-url")
    public String myURL() {
        return "snipp/shorturl";
    }
}
