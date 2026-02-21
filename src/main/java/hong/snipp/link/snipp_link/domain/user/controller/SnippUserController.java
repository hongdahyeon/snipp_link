package hong.snipp.link.snipp_link.domain.user.controller;

import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.domain.user.service.SnippUserService;
import hong.snipp.link.snipp_link.global.auth.dto.SnippSessionUser;
import hong.snipp.link.snipp_link.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.controller
 * fileName       : SnippUserController
 * author         : work
 * date           : 2025-04-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 * 2026-02-05        work       {/my-profile} > 유저 데이터 모델 내려주기
 * 2026-02-22        home       패키지 구조 변경
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/snipp/user")
public class SnippUserController {

    private final SnippUserService service;

    @GetMapping("/super")
    public String index() {
        return "super/user/index";
    }

    @GetMapping("/my-profile")
    public String profile(Model model) {
        SnippSessionUser loginUser = UserUtil.getLoginUser();
        SnippUserView user = service.findUserByUserUid(loginUser.getUid());
        model.addAttribute("user", user);
        return "snipp/profile";
    }
}
