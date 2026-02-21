package hong.snipp.link.snipp_link.domain.bbs.controller;

import hong.snipp.link.snipp_link.domain.bbs.entity.SnippBbs;
import hong.snipp.link.snipp_link.domain.bbs.service.SnippBbsService;
import hong.snipp.link.snipp_link.domain.enumcode.BbsTp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.controller
 * fileName       : SnippBbsController
 * author         : work
 * date           : 2025-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 * 2025-05-29        work       form / save 추가
 * 2026-02-22        home       패키지 구조 변경
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/snipp/bbs")
public class SnippBbsController {

    private final SnippBbsService service;

    @GetMapping("/super")
    public String index() {
        return "super/bbs/index";
    }

    @GetMapping("/super/{uid}")
    public String form(@PathVariable("uid") Long uid, Model model) {
        SnippBbs view = service.view(uid);
        model.addAttribute("view", view);
        model.addAttribute("bbsTpCds", BbsTp.toList());
        return "super/bbs/form";
    }

    @GetMapping("/super/save")
    public String save(Model model) {
        model.addAttribute("bbsTpCds", BbsTp.toList());
        return "super/bbs/save";
    }
}
