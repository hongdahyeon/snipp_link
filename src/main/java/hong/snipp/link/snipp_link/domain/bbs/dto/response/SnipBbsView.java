package hong.snipp.link.snipp_link.domain.bbs.dto.response;

import hong.snipp.link.snipp_link.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.dto.response
 * fileName       : SnipBbsView
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 단건 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipBbsView extends AuditMetaData {

    private Long bbsUid;
    private String bbsTp;
    private String bbsNm;
    private String useAt;
    private String deleteAt;
}
