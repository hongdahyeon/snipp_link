package hong.snipp.link.snipp_link.domain.comment.dto.response;

import hong.snipp.link.snipp_link.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.dto.response
 * fileName       : SnipCommentList
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipCommentList extends AuditMetaData {

    private Long commentUid;
    private Long boardUid;
    private String content;

}
