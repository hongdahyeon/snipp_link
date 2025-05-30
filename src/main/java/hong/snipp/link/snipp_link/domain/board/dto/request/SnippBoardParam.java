package hong.snipp.link.snipp_link.domain.board.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.dto.request
 * fileName       : SnippBoardParam
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 목록 조회 요청 파라미터 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2025-05-30        work       {clUid} 필드 추가
 */
@Getter @Setter
public class SnippBoardParam {

    private String title;

    private Long bbsUid;

    private Long clUid;
}
