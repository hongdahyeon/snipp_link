package hong.snipp.link.snipp_link.domain.bbscl.domain;

import hong.snipp.link.snipp_link.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.domain
 * fileName       : SnippBbsCl
 * author         : work
 * date           : 2025-05-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-30        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBbsCl extends AuditBean {

    private Long uid;
    private String clNm;
    private Long upperCl;
    private Long bbsUid;
    private Long sortNo;
}

