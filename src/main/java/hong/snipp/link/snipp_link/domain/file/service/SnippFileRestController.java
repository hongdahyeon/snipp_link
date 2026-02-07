package hong.snipp.link.snipp_link.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.file.service
 * fileName       : SnippFileRestController
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/file")
public class SnippFileRestController {

    private final SnippFileService service;
}