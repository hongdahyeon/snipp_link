package hong.snipp.link.snipp_link.global.bean.audit;

import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.global.bean.audit
 * fileName       : AuditBean
 * author         : work
 * date           : 2025-04-15
 * description    : 작성자 수정자 bean
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter
public class AuditBean {

    private Long regUid;
    private Long updtUid;

    public void setAuditBean(Long userUid) {
        this.regUid = userUid;
        this.updtUid = userUid;
    }
}
