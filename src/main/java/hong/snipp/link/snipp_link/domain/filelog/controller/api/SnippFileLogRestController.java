package hong.snipp.link.snipp_link.domain.filelog.controller.api;

import hong.snipp.link.snipp_link.domain.filelog.service.SnippFileLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.filelog.controller.api
 * fileName       : SnippFileLogRestController
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 로그 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/login-hist")
public class SnippFileLogRestController {

    private final SnippFileLogService logService;
}
