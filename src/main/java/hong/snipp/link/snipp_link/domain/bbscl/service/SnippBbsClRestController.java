package hong.snipp.link.snipp_link.domain.bbscl.service;

import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClChange;
import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClParam;
import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClSave;
import hong.snipp.link.snipp_link.domain.bbscl.dto.response.SnippBbsClList;
import hong.snipp.link.snipp_link.domain.bbscl.dto.response.SnippBbsClTree;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.service
 * fileName       : SnippBbsClRestController
 * author         : work
 * date           : 2025-05-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-30        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/bbs-cl")
public class SnippBbsClRestController {

    private final SnippBbsClService service;

    /**
     *
     * 분류 정보 리스트 형태 조회
     * =>
     *
     * @api         [GET] /snipp/api/bbs-cl/list
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
     * @api         [GET] /snipp/api/bbs-cl/tree
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
     * @api         [POST] /snipp/spi/bbs-cl
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
     * @api         [PUT] /snipp/api/bbs-cl/{uid}
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
     * @api         [DELETE] /snipp/api/bbs-cl/{uid}
     * @author      dahyeon
     * @date        2025-05-30
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteBbsCl(@PathVariable("uid") Long uid) {
        service.deleteBbsCl(uid);
        return ResponseEntity.ok().build();
    }
}
