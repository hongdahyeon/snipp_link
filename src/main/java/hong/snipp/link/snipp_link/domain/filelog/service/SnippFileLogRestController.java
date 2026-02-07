package hong.snipp.link.snipp_link.domain.filelog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.filelog.service
 * fileName       : SnippFileLogRestController
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 로그 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/login-hist")
public class SnippFileLogRestController {

    private final SnippFileLogService logService;
}
