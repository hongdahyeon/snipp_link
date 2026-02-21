package hong.snipp.link.snipp_link.domain.bbs.controller.api;

import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnippBbsChange;
import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnippBbsParam;
import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnippBbsSave;
import hong.snipp.link.snipp_link.domain.bbs.dto.response.SnippBbsList;
import hong.snipp.link.snipp_link.domain.bbs.dto.response.SnippBbsView;
import hong.snipp.link.snipp_link.domain.bbs.service.SnippBbsService;
import hong.snipp.link.snipp_link.domain.enumcode.BbsTp;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import hong.snipp.link.snipp_link.global.exception.SnippException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.controller.api
 * fileName       : SnippBbsRestController
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2026-02-05        work       /snipp/api/bbs => /api/snipp/bbs
 * 2026-02-22        home       패키지 구조 변경
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/bbs")
public class SnippBbsRestController {

    private final SnippBbsService service;

    /**
     *
     * 게시판 저장
     *
     * @api         [POST] /api/snipp/bbs
     * @author      work
     * @date        2025-04-15
    **/
    @PostMapping
    public ResponseEntity saveBbs(@RequestBody @Valid SnippBbsSave request) {
        if( !BbsTp.isValidCode(request.getBbsTp()) ) {
            throw new SnippException("유효하지 않은 게시판 유형 코드입니다.", HttpStatus.BAD_REQUEST);
        }
        service.saveBbs(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시판 수정
     *
     * @api         [PUT] /api/snipp/bbs/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeBbs(@PathVariable("uid") Long uid, @RequestBody @Valid SnippBbsChange request) {
        service.changeBbs(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * {uid} 값으로 게시판 단건 조회
     *
     * @api         [GET] /api/snipp/bbs/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/{uid}")
    public ResponseEntity findBbsByUid(@PathVariable("uid") Long uid) {
        SnippBbsView bbsByUid = service.findBbsByUid(uid);
        return ResponseEntity.ok(bbsByUid);
    }

    /**
     *
     * 게시판 목록 조회 (페이징)
     *
     * @api         [GET] /api/snipp/bbs/page
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/page")
    public ResponseEntity findAllBbsPage(@Valid SnippBbsParam param, Pageable pageable) {
        Page<SnippBbsList> allBbsPage = service.findAllBbsPage(param, pageable);
        return ResponseEntity.ok(allBbsPage);
    }

    /**
     *
     * 게시판 목록 조회 (리스트)
     *
     * @api         [GET] /api/snipp/bbs/list
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/list")
    public ResponseEntity findAllBbsList(@Valid SnippBbsParam param) {
        List<SnippBbsList> allBbsList = service.findAllBbsList(param);
        return ResponseEntity.ok(allBbsList);
    }

    /**
     *
     * 게시판 단건 삭제
     *
     * @api         [DELETE] /api/snipp/bbs/delete
     * @author      work
     * @date        2025-04-15
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteBbs(@PathVariable("uid") Long uid) {
        service.deleteBbs(uid);
        return ResponseEntity.ok().build();
    }
}
