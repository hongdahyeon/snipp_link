package hong.snipp.link.snipp_link.domain.loginhist.service;

import hong.snipp.link.snipp_link.domain.loginhist.dto.request.SnippLoginHistParam;
import hong.snipp.link.snipp_link.domain.loginhist.dto.response.SnippLoginHistList;
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
 * packageName    : hong.snipp.link.snipp_link.domain.loginhist.service
 * fileName       : SnippLoginHistRestController
 * author         : work
 * date           : 2025-04-28
 * description    : 로그인 이력 조회 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-28        work       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/login-hist")
public class SnippLoginHistRestController {

    private final SnippLoginHistService service;

    /**
     *
     * 로그인 이력 목록 조회 (페이징)
     *
     * @api         [GET] /snipp/api/login-hist/page
     * @author      work
     * @date        2025-04-28
    **/
    @GetMapping("/page")
    public ResponseEntity findAllLoginHistPage(@Valid SnippLoginHistParam param, Pageable pageable) {
        Page<SnippLoginHistList> allLoginHistPage = service.findAllLoginHistPage(param, pageable);
        return ResponseEntity.ok(allLoginHistPage);
    }

    /**
     *
     * 로그인 이력 목록 조회 (리스트)
     *
     * @api         [GET] /snipp/api/login-hist/page
     * @author      work
     * @date        2025-04-28
     **/
    @GetMapping("/list")
    public ResponseEntity findAllLoginHistList(@Valid SnippLoginHistParam param) {
        List<SnippLoginHistList> allLoginHistPage = service.findAllLoginHistList(param);
        return ResponseEntity.ok(allLoginHistPage);
    }
}
