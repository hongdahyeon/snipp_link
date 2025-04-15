package hong.snipp.link.snipp_link.domain.comment.service;

import hong.snipp.link.snipp_link.domain.comment.dto.request.SnipCommentChange;
import hong.snipp.link.snipp_link.domain.comment.dto.request.SnipCommentSave;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.service
 * fileName       : SnipCommentRestController
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/comment")
public class SnipCommentRestController {

    private final SnipCommentService service;

    /**
     *
     * 댓글 저장
     *
     * @api         [POST] /snipp/api/comment
     * @author      work
     * @date        2025-04-15
    **/
    @PostMapping
    public ResponseEntity saveComment(@RequestBody @Valid SnipCommentSave request) {
        service.saveComment(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 댓글 수정
     *
     * @api         [PUT] /snipp/api/comment/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeComment(@PathVariable("uid") Long uid, @RequestBody @Valid SnipCommentChange request) {
        service.changeComment(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 댓글 삭제
     *
     * @api         [DELETE] /snipp/api/comment/{uid}
     * @author      work
     * @date        2025-04-15
    **/
    @DeleteMapping("/{uid}")
    public ResponseEntity deleteComment(@PathVariable("uid") Long uid) {
        service.deleteComment(uid);
        return ResponseEntity.ok().build();
    }


}
