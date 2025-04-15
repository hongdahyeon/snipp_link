package hong.snipp.link.snipp_link.global.bean.audit;

import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.global.bean.audit
 * fileName       : AuditMetaData
 * author         : work
 * date           : 2025-04-15
 * description    : 작성자 & 수정자 정보 조회
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */

@Getter
public class AuditMetaData {

    private Long regUid;
    private String regDt;
    private String regId;
    private Long updtUid;
    private String updtDt;
    private String updtId;

}
