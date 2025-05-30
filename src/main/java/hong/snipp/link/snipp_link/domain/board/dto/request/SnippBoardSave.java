package hong.snipp.link.snipp_link.domain.board.dto.request;

import hong.snipp.link.snipp_link.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.dto.request
 * fileName       : SnippBoardSave
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2025-05-30        work       useAt, thumbnailSrc 필드 추가
 * 2025-05-30        work       {clUid} 필드 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBoardSave {

    @NotNull
    private Long bbsUid;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @YorN
    private String useAt;

    private String thumbnailSrc;

    private Long clUid;
}
