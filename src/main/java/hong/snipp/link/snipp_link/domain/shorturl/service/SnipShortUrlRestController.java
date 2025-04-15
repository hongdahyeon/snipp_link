package hong.snipp.link.snipp_link.domain.shorturl.service;

import hong.snipp.link.snipp_link.domain.shorturl.dto.request.SnipShortUrlCreate;
import hong.snipp.link.snipp_link.domain.shorturl.dto.request.SnipShortUrlParam;
import hong.snipp.link.snipp_link.domain.shorturl.dto.response.SnipShortUrlList;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import hong.snipp.link.snipp_link.global.hong.shortURL.CreateShortURLService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.service
 * fileName       : SnipShortUrlRestController
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/short-url")
public class SnipShortUrlRestController {

    private final SnipShortUrlService service;
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
    public ResponseEntity createShortURL(@RequestBody @Valid SnipShortUrlCreate request) {
        String shortURL = createShortURLService.generateShortURL(request);
        return ResponseEntity.ok(shortURL);
    }

    /**
     *
     * SHORT_URL 목록 조회 (페이징)
     *
     * @api         [GET] /snipp/api/short-url/page
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/page")
    public ResponseEntity findAllShortURLPage(@Valid SnipShortUrlParam param, Pageable pageable) {
        Page<SnipShortUrlList> allShortURLPage = service.findAllShortURLPage(param, pageable);
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
    public ResponseEntity findAllShortURLList(@Valid SnipShortUrlParam param) {
        List<SnipShortUrlList> allShortURLList = service.findAllShortURLList(param);
        return ResponseEntity.ok(allShortURLList);
    }
}
