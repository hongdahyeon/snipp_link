package hong.snipp.link.snipp_link.domain.filelog.domain;

import hong.snipp.link.snipp_link.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.filelog.domain
 * fileName       : SnippFileLog
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 로그 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippFileLog extends AuditBean {

    private Long uid;
    private String fileId;
}
