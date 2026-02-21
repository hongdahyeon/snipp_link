package hong.snipp.link.snipp_link.domain.comment.controller.api;

import hong.snipp.link.snipp_link.domain.comment.dto.request.SnippCommentChange;
import hong.snipp.link.snipp_link.domain.comment.dto.request.SnippCommentSave;
import hong.snipp.link.snipp_link.domain.comment.service.SnippCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.controller.api
 * fileName       : SnippCommentRestController
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2026-02-05        work       /snipp/api/comment => /api/snipp/comment
 * 2026-02-22        home       패키지 구조 변경
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/comment")
public class SnippCommentRestController {

    private final SnippCommentService service;

    /**
     *
     * 댓글 저장
     *
     * @api         [POST] /api/snipp/comment
     * @author      work
     * @date        2025-04-15
    **/
    @PostMapping
    public ResponseEntity saveComment(@RequestBody @Valid SnippCommentSave request) {
        service.saveComment(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 댓글 수정
     *
     * @api         [PUT] /api/snipp/comment/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeComment(@PathVariable("uid") Long uid, @RequestBody @Valid SnippCommentChange request) {
        service.changeComment(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 댓글 삭제
     *
     * @api         [DELETE] /api/snipp/comment/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteComment(@PathVariable("uid") Long uid) {
        service.deleteComment(uid);
        return ResponseEntity.ok().build();
    }


}
