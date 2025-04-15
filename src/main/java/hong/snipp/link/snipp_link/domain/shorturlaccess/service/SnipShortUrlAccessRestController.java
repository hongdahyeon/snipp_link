package hong.snipp.link.snipp_link.domain.shorturlaccess.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccess.service
 * fileName       : SnipShortUrlAccessRestController
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 권한 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/short-url/access")
public class SnipShortUrlAccessRestController {

    private final SnipShortUrlAccessService service;
}
