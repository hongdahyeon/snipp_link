package hong.snipp.link.snipp_link.domain.comment.entity;

import hong.snipp.link.snipp_link.domain.comment.dto.request.SnippCommentChange;
import hong.snipp.link.snipp_link.domain.comment.dto.request.SnippCommentSave;
import hong.snipp.link.snipp_link.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.entity
 * fileName       : SnippComment
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2026-02-22        home       패키지 구조 변경
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippComment extends AuditBean {

    private Long uid;
    private Long boardUid;
    private String content;
    private String deleteAt;

    /**
     * @method      SnippComment 생성자 1
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 저장용 생성자
    **/
    public SnippComment(SnippCommentSave request) {
        this.boardUid = request.getBoardUid();
        this.content = request.getContent();
    }

    /**
     * @method      SnippComment 생성자 2
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 수정용 생성자
    **/
    public SnippComment(Long uid, SnippCommentChange request) {
        this.uid = uid;
        this.content = request.getContent();
    }

    /**
     * @method      SnippComment 생성자 3
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 삭제용 생성자
    **/
    public SnippComment(Long uid) {
        this.uid = uid;
    }
}
