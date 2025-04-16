package hong.snipp.link.snipp_link.domain.bbs.domain;

import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnippBbsChange;
import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnippBbsSave;
import hong.snipp.link.snipp_link.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.domain
 * fileName       : SnippBbs
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBbs extends AuditBean {

    private Long uid;
    private String bbsTp;
    private String bbsNm;
    private String useAt;
    private String deleteAt;

    /**
     * @method      SnippBbs 생성자 1
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 저장용 생성자
    **/
    public SnippBbs(SnippBbsSave request) {
        this.bbsTp = request.getBbsTp();
        this.bbsNm = request.getBbsNm();
        this.useAt = request.getUseAt();
    }

    /**
     * @method      SnippBbs 생성자 2
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 수정용 생성자
    **/
    public SnippBbs(Long uid, SnippBbsChange request) {
        this.uid = uid;
        this.bbsNm = request.getBbsNm();
        this.useAt = request.getUseAt();
    }

    /**
     * @method      SnippBbs 생성자 3
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 삭제용 생성자
    **/
    public SnippBbs(Long uid) {
        this.uid = uid;
    }

}
