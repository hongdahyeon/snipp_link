package hong.snipp.link.snipp_link.domain.board.service;

import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardChange;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardParam;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardSave;
import hong.snipp.link.snipp_link.domain.board.dto.response.SnippBoardList;
import hong.snipp.link.snipp_link.domain.board.dto.response.SnippBoardView;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.service
 * fileName       : SnippBoardRestController
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2025-05-30        work       {findBoardCntUseCl} api 추가
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/board")
public class SnippBoardRestController {

    private final SnippBoardService service;

    /**
     *
     * 게시글 저장
     *
     * @api         [POST] /snipp/api/board
     * @author      work
     * @date        2025-04-15
    **/
    @PostMapping
    public ResponseEntity saveBoard(@RequestBody @Valid SnippBoardSave request) {
        service.saveBoard(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시글 수정
     *
     * @api         [PUT] /snipp/api/board/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeBoard(@PathVariable("uid") Long uid, @RequestBody @Valid SnippBoardChange request) {
        service.changeBoard(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시글 단건 조회
     *
     * @api         [GET] /snipp/api/board/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/{uid}")
    public ResponseEntity findBoardByUid(@PathVariable("uid") Long uid) {
        SnippBoardView boardByUid = service.findBoardByUid(uid);
        return ResponseEntity.ok(boardByUid);
    }

    /**
     *
     * 게시글 목록 조회 (페이징)
     *
     * @api         [GET] /snipp/api/board/page
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/page")
    public ResponseEntity findAllBoardPage(@Valid SnippBoardParam param, Pageable pageable) {
        Page<SnippBoardList> allBoardPage = service.findAllBoardPage(param, pageable);
        return ResponseEntity.ok(allBoardPage);
    }

    /**
     *
     * 게시글 목록 조회 (리스트)
     *
     * @api         [GET] /snipp/api/board/list
     * @author      work
     * @date        2025-04-15
    **/
    @GetMapping("/list")
    public ResponseEntity findAllBoardList(@Valid SnippBoardParam param) {
        List<SnippBoardList> allBoardList = service.findAllBoardList(param);
        return ResponseEntity.ok(allBoardList);
    }

    /**
     *
     * 게시글 단건 삭제
     *
     * @api         [DELETE] /snipp/api/board/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteBoard(@PathVariable("uid") Long uid) {
        service.deleteBoard(uid);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * {clUid} 분류를 이용하는 게시글 개수 카운팅
     *
     * @api         [GET] /snipp/api/board/child/{uid}
     * @author      dahyeon
     * @date        2025-05-30
    **/
    @GetMapping("/child/{uid}")
    public ResponseEntity findBoardCntUseCl(@PathVariable("uid") Long uid) {
        int boardCntUseCl = service.findBoardCntUseCl(uid);
        return ResponseEntity.ok(boardCntUseCl);
    }
}
