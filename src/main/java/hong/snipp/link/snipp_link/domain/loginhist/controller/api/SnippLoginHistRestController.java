package hong.snipp.link.snipp_link.domain.loginhist.controller.api;

import hong.snipp.link.snipp_link.domain.loginhist.dto.request.SnippLoginHistParam;
import hong.snipp.link.snipp_link.domain.loginhist.dto.response.SnippLoginHistList;
import hong.snipp.link.snipp_link.domain.loginhist.service.SnippLoginHistService;
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
 * packageName    : hong.snipp.link.snipp_link.domain.loginhist.controller.api
 * fileName       : SnippLoginHistRestController
 * author         : work
 * date           : 2025-04-28
 * description    : 로그인 이력 조회 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-28        work       최초 생성
 * 2026-01-12        home       super 권한 추가
 * 2026-02-05        work       /snipp/api/login-hist => /api/snipp/login-hist
 * 2026-02-22        home       패키지 구조 변경
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/login-hist")
public class SnippLoginHistRestController {

    private final SnippLoginHistService service;

    /**
     *
     * 로그인 이력 목록 조회 (페이징)
     *
     * @api         [GET] /api/snipp/login-hist/super/page
     * @author      work
     * @date        2025-04-28
    **/
    @GetMapping("/super/page")
    public ResponseEntity findAllLoginHistPage(@Valid SnippLoginHistParam param, Pageable pageable) {
        Page<SnippLoginHistList> allLoginHistPage = service.findAllLoginHistPage(param, pageable);
        return ResponseEntity.ok(allLoginHistPage);
    }

    /**
     *
     * 로그인 이력 목록 조회 (리스트)
     *
     * @api         [GET] /api/snipp/login-hist/super/list
     * @author      work
     * @date        2025-04-28
     **/
    @GetMapping("/super/list")
    public ResponseEntity findAllLoginHistList(@Valid SnippLoginHistParam param) {
        List<SnippLoginHistList> allLoginHistPage = service.findAllLoginHistList(param);
        return ResponseEntity.ok(allLoginHistPage);
    }
}
