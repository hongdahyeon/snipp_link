package hong.snipp.link.snipp_link.domain.shorturl.shorturl.service;

import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.request.SnippShortUrlCreate;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.request.SnippShortUrlParam;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response.SnippShortUrlList;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import hong.snipp.link.snipp_link.global.hong.shortURL.CreateShortURLService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.shorturl.service
 * fileName       : SnippShortUrlRestController
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       파일 이름 변경 : snip -> snipp
 * 2026-01-12        home       short_url 생성
 * 2026-01-14        work       short_url page 조회 super 권한
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/short-url")
public class SnippShortUrlRestController {

    private final SnippShortUrlService service;
    private final CreateShortURLService createShortURLService;

    /**
     *
     * ORIGIN_URL -> SHORT_URL 생성
     *
     * @api         [POST] /snipp/api/short-url/create
     * @author      work
     * @date        2025-04-15
    **/
    @PostMapping("/create")
    public ResponseEntity createShortURL(@RequestBody @Valid SnippShortUrlCreate request) {
        String shortURL = createShortURLService.generateShortURL(request);
        return ResponseEntity.ok(shortURL);
    }

    /**
     *
     * SHORT_URL 목록 조회 (페이징)
     *
     * @api         [GET] /snipp/api/short-url/super/page
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/super/page")
    public ResponseEntity findAllShortURLPage(@Valid SnippShortUrlParam param, Pageable pageable) {
        Page<SnippShortUrlList> allShortURLPage = service.findAllShortURLPage(param, pageable);
        return ResponseEntity.ok(allShortURLPage);
    }

    /**
     *
     * SHORT_URL 목록 조회 (리스트)
     *
     * @api         [GET] /snipp/api/short-url/list
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/list")
    public ResponseEntity findAllShortURLList(@Valid SnippShortUrlParam param) {
        List<SnippShortUrlList> allShortURLList = service.findAllShortURLList(param);
        return ResponseEntity.ok(allShortURLList);
    }
}
