package hong.snipp.link.snipp_link.domain.board.controller.api;

import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardChange;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardParam;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardSave;
import hong.snipp.link.snipp_link.domain.board.dto.response.SnippBoardList;
import hong.snipp.link.snipp_link.domain.board.dto.response.SnippBoardView;
import hong.snipp.link.snipp_link.domain.board.service.SnippBoardService;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.controller.api
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
 * 2026-02-05        work       /snipp/api/board => /api/snipp/board
 * 2026-02-08        work       - {post} files 추가
 *                              - {put} files 추가
 * 2026-02-22        home       패키지 구조 변경
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/board")
public class SnippBoardRestController {

    private final SnippBoardService service;

    /**
     *
     * 게시글 저장
     *
     * @api         [POST] /api/snipp/board
     * @author      work
     * @date        2025-04-15
    **/
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity saveBoard(
            @RequestPart("request") @Valid SnippBoardSave request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        service.saveBoard(request, files);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시글 수정
     *
     * @api         [PUT] /api/snipp/board/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @PutMapping(value = "/{uid}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity changeBoard(
            @PathVariable("uid") Long uid,
            @RequestPart("request") @Valid SnippBoardChange request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        service.changeBoard(uid, request, files);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 게시글 단건 조회
     *
     * @api         [GET] /api/snipp/board/{uid}
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
     * @api         [GET] /api/snipp/board/page
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
     * @api         [GET] /api/snipp/board/list
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
     * @api         [DELETE] /api/snipp/board/{uid}
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
     * @api         [GET] /api/snipp/board/child/{uid}
     * @author      dahyeon
     * @date        2025-05-30
    **/
    @GetMapping("/child/{uid}")
    public ResponseEntity findBoardCntUseCl(@PathVariable("uid") Long uid) {
        int boardCntUseCl = service.findBoardCntUseCl(uid);
        return ResponseEntity.ok(boardCntUseCl);
    }
}
