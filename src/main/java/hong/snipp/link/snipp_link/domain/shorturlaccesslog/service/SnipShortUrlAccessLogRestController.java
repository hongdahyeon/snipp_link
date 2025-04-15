package hong.snipp.link.snipp_link.domain.shorturlaccesslog.service;

import hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.request.SnipShortUrlAccessLogParam;
import hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.response.SnipShortUrlAccessLogList;
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
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccesslog.service
 * fileName       : SnipShortUrlAccessLogRestController
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/short-url/access-log")
public class SnipShortUrlAccessLogRestController {

    private final SnipShortUrlAccessLogService service;

    /**
     *
     * SHORT_URL 접근 로그 목록 조회 (페이징)
     *
     * @api         [GET] /snipp/api/short-url/access-log/page
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/page")
    public ResponseEntity findAllLogPage(@Valid SnipShortUrlAccessLogParam param, Pageable pageable) {
        Page<SnipShortUrlAccessLogList> allLogPage = service.findAllLogPage(param, pageable);
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
    public ResponseEntity findAllLogList(@Valid SnipShortUrlAccessLogParam param) {
        List<SnipShortUrlAccessLogList> allLogList = service.findAllLogList(param);
        return ResponseEntity.ok(allLogList);
    }
}
