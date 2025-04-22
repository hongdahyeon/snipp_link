package hong.snipp.link.snipp_link.domain.board.web;

import hong.snipp.link.snipp_link.domain.board.service.SnippBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.web
 * fileName       : SnippBoardController
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
@RequestMapping("/snipp/board")
public class SnippBoardController {

    private final SnippBoardService service;

    @GetMapping("/faq")
    public String faq() {
        return "board/faq/index";
    }

    @GetMapping("/qna")
    public String qna() {
        return "board/qna/index";
    }

    @GetMapping("/free")
    public String free() {
        return "board/free/index";
    }

    @GetMapping("/notice")
    public String notice() {
        return "board/notice/index";
    }
}
