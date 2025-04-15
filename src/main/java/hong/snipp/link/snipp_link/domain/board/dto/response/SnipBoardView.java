package hong.snipp.link.snipp_link.domain.board.dto.response;

import hong.snipp.link.snipp_link.domain.comment.dto.response.SnipCommentList;
import hong.snipp.link.snipp_link.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.dto.response
 * fileName       : SnipBoardView
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 단건 조회 응답 DTO (댓글도 같이 조회)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipBoardView extends AuditMetaData {

    private Long boardUid;
    private Long bbsUid;
    private String title;
    private String content;
    private String deleteAt;
    List<SnipCommentList> comments = new ArrayList<>();

    public void setComments(List<SnipCommentList> comments) {
        this.comments = comments;
    }
}
