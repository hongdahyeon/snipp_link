package hong.snipp.link.snipp_link.domain.socialuser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.socialuser.service
 * fileName       : SnippSocialUserRestController
 * author         : work
 * date           : 2025-04-15
 * description    : 소셜 유저 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2026-02-05        work       /snipp/api/social-user => /api/snipp/social-user
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/social-user")
public class SnippSocialUserRestController {

    private final SnippSocialUserService service;
}
