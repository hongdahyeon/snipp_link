package hong.snipp.link.snipp_link.domain.comment.domain;

import hong.snipp.link.snipp_link.domain.comment.dto.request.SnipCommentChange;
import hong.snipp.link.snipp_link.domain.comment.dto.request.SnipCommentSave;
import hong.snipp.link.snipp_link.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.domain
 * fileName       : SnipComment
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipComment extends AuditBean {

    private Long uid;
    private Long boardUid;
    private String content;
    private String deleteAt;

    /**
     * @method      SnipComment 생성자 1
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 저장용 생성자
    **/
    public SnipComment(SnipCommentSave request) {
        this.boardUid = request.getBoardUid();
        this.content = request.getContent();
    }

    /**
     * @method      SnipComment 생성자 2
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 수정용 생성자
    **/
    public SnipComment(Long uid, SnipCommentChange request) {
        this.uid = uid;
        this.content = request.getContent();
    }

    /**
     * @method      SnipComment 생성자 3
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 삭제용 생성자
    **/
    public SnipComment(Long uid) {
        this.uid = uid;
    }
}
