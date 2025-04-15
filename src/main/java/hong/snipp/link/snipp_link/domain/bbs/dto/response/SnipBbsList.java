package hong.snipp.link.snipp_link.domain.bbs.dto.response;

import hong.snipp.link.snipp_link.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.dto.response
 * fileName       : SnipBbsList
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 목록 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipBbsList extends AuditMetaData {

    private Long bbsUid;
    private String bbsTp;
    private String bbsNm;
    private String useAt;
    private String deleteAt;
}
