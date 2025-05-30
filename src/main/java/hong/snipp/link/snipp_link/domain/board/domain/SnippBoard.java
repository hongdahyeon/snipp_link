package hong.snipp.link.snipp_link.domain.board.domain;

import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardChange;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardSave;
import hong.snipp.link.snipp_link.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.domain
 * fileName       : SnippBoard
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2025-05-30        work       useAt, thumbnailSrc 필드 추가
 * 2025-05-30        work       {clUid} 필드 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBoard extends AuditBean {

    private Long uid;
    private Long bbsUid;
    private Long clUid;
    private String title;
    private String content;
    private String useAt;
    private String thumbnailSrc;
    private String deleteAt;

    /**
     * @method      SnippBoard 생성자 1
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 저장용 생성자
    **/
    public SnippBoard(SnippBoardSave request) {
        this.bbsUid = request.getBbsUid();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.useAt = request.getUseAt();
        this.thumbnailSrc = request.getThumbnailSrc();
        this.clUid = request.getClUid();
    }

    /**
     * @method      SnippBoard 생성자 2
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 수정용 생성자
    **/
    public SnippBoard(Long uid, SnippBoardChange request) {
        this.uid = uid;
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    /**
     * @method      SnippBoard 생성자 3
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 삭제용 생성자
    **/
    public SnippBoard(Long boardUid) {
        this.uid = boardUid;
    }

}
