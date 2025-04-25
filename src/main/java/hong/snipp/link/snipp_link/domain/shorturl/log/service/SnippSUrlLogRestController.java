package hong.snipp.link.snipp_link.domain.shorturl.log.service;

import hong.snipp.link.snipp_link.domain.shorturl.log.dto.request.SnippSUrlLogParam;
import hong.snipp.link.snipp_link.domain.shorturl.log.dto.response.SnippSUrlLogList;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.log.service
 * fileName       : SnippSUrlLogRestController
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccessLog -> SUrlLog
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/short-url/access-log")
public class SnippSUrlLogRestController {

    private final SnippSUrlLogService service;

    /**
     *
     * SHORT_URL 접근 로그 목록 조회 (페이징)
     *
     * @api         [GET] /snipp/api/short-url/access-log/page
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/page")
    public ResponseEntity findAllLogPage(@Valid SnippSUrlLogParam param, Pageable pageable) {
        Page<SnippSUrlLogList> allLogPage = service.findAllLogPage(param, pageable);
        return ResponseEntity.ok(allLogPage);
    }

    /**
     *
     * SHORT_URL 접근 로그 목록 조회 (리스트)
     *
     * @api         [GET] /snipp/api/short-url/access-log/list
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/list")
    public ResponseEntity findAllLogList(@Valid SnippSUrlLogParam param) {
        List<SnippSUrlLogList> allLogList = service.findAllLogList(param);
        return ResponseEntity.ok(allLogList);
    }
}
