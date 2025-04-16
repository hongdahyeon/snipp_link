package hong.snipp.link.snipp_link.domain.shorturl.access.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.access.service
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
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/short-url/access")
public class SnippSUrlAccessRestController {

    private final SnippSUrlAccessService service;
}
