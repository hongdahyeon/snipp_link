package hong.snipp.link.snipp_link.domain.bbscl.entity;

import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClChange;
import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClSave;
import hong.snipp.link.snipp_link.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.entity
 * fileName       : SnippBbsCl
 * author         : work
 * date           : 2025-05-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-30        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBbsCl extends AuditBean {

    private Long uid;
    private String clNm;
    private Long upperCl;
    private Long bbsUid;
    private Long sortNo;

    /**
     * @method      SnippBbsCl 생성자 1
     * @author      dahyeon
     * @date        2025-05-30
     * @deacription 분류 저장용 생성자 1
    **/
    public SnippBbsCl(SnippBbsClSave request) {
        this.clNm = request.getClNm();
        this.upperCl = request.getUpperCl();
        this.bbsUid = request.getBbsUid();
        this.sortNo = request.getSortNo();
    }

    /**
     * @method      changeBbsCl 생성자 2
     * @author      dahyeon
     * @date        2025-05-30
     * @deacription 분류 수정용 생성자 2
    **/
    public SnippBbsCl changeBbsCl(Long uid, SnippBbsClChange request) {
        this.uid = uid;
        this.clNm = (request.getClNm() != null) ? request.getClNm() : this.clNm;
        this.sortNo = (request.getSortNo() != null) ? request.getSortNo() : this.sortNo;
        return this;
    }
}

