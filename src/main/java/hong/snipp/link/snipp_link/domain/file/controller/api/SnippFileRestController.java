package hong.snipp.link.snipp_link.domain.file.controller.api;

import hong.snipp.link.snipp_link.domain.file.dto.request.SnippFileParam;
import hong.snipp.link.snipp_link.domain.file.dto.response.SnippFileList;
import hong.snipp.link.snipp_link.domain.file.service.SnippFileService;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.file.controller.api
 * fileName       : SnippFileRestController
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 * 2026-02-08        work       파일 페이징 조회 API 추가
 * 2026-02-22        home       패키지 구조 변경
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/file")
public class SnippFileRestController {

    private final SnippFileService service;

    /**
     *
     * 파일 페이징 조회
     *
     * @api         [GET] /api/snipp/file/page
     * @author      dahyeon
     * @date        2026-02-08
    **/
    @GetMapping("/page")
    public ResponseEntity findAllFilePage(
            @Valid SnippFileParam param,
            Pageable pageable
    ) {
        Page<SnippFileList> findAllFilePage = service.findAllFilePageByFileUid(param, pageable);
        return ResponseEntity.ok(findAllFilePage);
    }
}