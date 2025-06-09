package hong.snipp.link.snipp_link.domain.board.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.dto.response
 * fileName       : SnippBoardDetail
 * author         : work
 * date           : 2025-06-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-09        work       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBoardDetail {

    private Long boardUid;
    private Long bbsUid;
    private Long clUid;
    private String title;
    private String content;
    private String deleteAt;
}
