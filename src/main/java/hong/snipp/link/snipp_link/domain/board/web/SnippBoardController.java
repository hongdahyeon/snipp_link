package hong.snipp.link.snipp_link.domain.board.web;

import hong.snipp.link.snipp_link.domain.bbs.domain.SnippBbs;
import hong.snipp.link.snipp_link.domain.bbs.service.SnippBbsService;
import hong.snipp.link.snipp_link.domain.board.service.SnippBoardService;
import hong.snipp.link.snipp_link.domain.code.BbsTp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * 2025-05-30        work       index, view, form, save 메소드 추가
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/snipp/board")
public class SnippBoardController {

    private final SnippBoardService service;
    private final SnippBbsService bbsService;

    @GetMapping("/{type}")
    public String index(@PathVariable("type") String type, Model model) {
        BbsTp code = BbsTp.getBbsTpByText(type);
        if(code == null) return "error/error";

        SnippBbs recentBbs = bbsService.findRecentBbs(code.name());
        if(recentBbs == null) return "error/error";

        model.addAttribute("recentBbs", recentBbs);
        return "board/index";
    }

    @GetMapping("/{type}/{boardUid}")
    public String view(@PathVariable("type") String type, @PathVariable("boardUid") Long boardUid, Model model) {
        BbsTp code = BbsTp.getBbsTpByText(type);
        if(code == null) return "error/error";

        SnippBbs recentBbs = bbsService.findRecentBbs(code.name());
        if(recentBbs == null) return "error/error";

        model.addAttribute("recentBbs", recentBbs);
        return "board/view";
    }

    @GetMapping("/{type}/form/{boardUid}")
    public String form(@PathVariable("type") String type, @PathVariable("boardUid") Long boardUid, Model model) {
        BbsTp code = BbsTp.getBbsTpByText(type);
        if(code == null) return "error/error";

        SnippBbs recentBbs = bbsService.findRecentBbs(code.name());
        if(recentBbs == null) return "error/error";

        model.addAttribute("recentBbs", recentBbs);

        model.addAttribute("form", service.findDetailOfBoard(boardUid));

        return "board/form";
    }

    @GetMapping("/{type}/save")
    public String save(@PathVariable("type") String type, Model model) {
        BbsTp code = BbsTp.getBbsTpByText(type);
        if(code == null) return "error/error";

        SnippBbs recentBbs = bbsService.findRecentBbs(code.name());
        if(recentBbs == null) return "error/error";

        model.addAttribute("recentBbs", recentBbs);
        return "board/save";
    }
}
