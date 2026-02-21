package hong.snipp.link.snipp_link.domain.shorturl.access.controller.api;

import hong.snipp.link.snipp_link.domain.shorturl.access.service.SnippSUrlAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.access.controller.api
 * fileName       : SnippSUrlAccessRestController
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 권한 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccess -> SUrlAccess
 * 2026-02-05        work       /snipp/api/short-url/access => /api/snipp/short-url/access
 * 2026-02-22        home       패키지 구조 변경
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/short-url/access")
public class SnippSUrlAccessRestController {

    private final SnippSUrlAccessService service;
}
