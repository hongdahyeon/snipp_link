package hong.snipp.link.snipp_link.domain.bbscl.controller.api;

import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClChange;
import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClParam;
import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClSave;
import hong.snipp.link.snipp_link.domain.bbscl.dto.response.SnippBbsClList;
import hong.snipp.link.snipp_link.domain.bbscl.dto.response.SnippBbsClTree;
import hong.snipp.link.snipp_link.domain.bbscl.service.SnippBbsClService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.controller.api
 * fileName       : SnippBbsClRestController
 * author         : work
 * date           : 2025-05-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-30        work       최초 생성
 * 2026-02-05        work       /snipp/api/bbs-cl => /api/snipp/bbs-cl
 * 2026-02-22        home       패키지 구조 변경
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/bbs-cl")
public class SnippBbsClRestController {

    private final SnippBbsClService service;

    /**
     *
     * 분류 정보 리스트 형태 조회
     * =>
     *
     * @api         [GET] /api/snipp/bbs-cl/list
     * @author      dahyeon
     * @date        2025-05-30
    **/
    @GetMapping("/list")
    public ResponseEntity clList(@Valid SnippBbsClParam param) {
        List<SnippBbsClList> snippBbsClLists = service.findBbsClList(param);
        return ResponseEntity.ok(snippBbsClLists);
    }

    /**
     *
     * 분류 정보 리스트 형태 조회 => 트리 형태 변환후 리턴
     *
     * @api         [GET] /api/snipp/bbs-cl/tree
     * @author      dahyeon
     * @date        2025-05-30
    **/
    @GetMapping("/tree")
    public ResponseEntity clTree(@Valid SnippBbsClParam param) {
        List<SnippBbsClTree> bbsClTree = service.findBbsClTree(param);
        return ResponseEntity.ok(bbsClTree);
    }

    /**
     *
     * 게시판 분류 정보 저장
     *
     * @api         [POST] /api/snipp/bbs-cl
     * @author      dahyeon
     * @date        2025-05-30
    **/
    @PostMapping
    public ResponseEntity saveBbsCl(@RequestBody @Valid SnippBbsClSave request) {
        service.saveBbsCl(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시판 분류 정보 수정
     *
     * @api         [PUT] /api/snipp/bbs-cl/{uid}
     * @author      dahyeon
     * @date        2025-05-30
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeBbsCl(@PathVariable("uid") Long uid, @RequestBody @Valid SnippBbsClChange request) {
        service.changeBbsCl(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시판 분류 정보 삭제
     *
     * @api         [DELETE] /api/snipp/bbs-cl/{uid}
     * @author      dahyeon
     * @date        2025-05-30
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteBbsCl(@PathVariable("uid") Long uid) {
        service.deleteBbsCl(uid);
        return ResponseEntity.ok().build();
    }
}
