package hong.snipp.link.snipp_link.domain.board.dto.response;

import hong.snipp.link.snipp_link.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.dto.response
 * fileName       : SnipBoardList
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipBoardList extends AuditMetaData {

    private Long boardUid;
    private Long bbsUid;
    private String title;
    private String content;
    private String deleteAt;

}