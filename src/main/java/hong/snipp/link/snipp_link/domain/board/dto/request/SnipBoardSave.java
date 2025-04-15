package hong.snipp.link.snipp_link.domain.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.dto.request
 * fileName       : SnipBoardSave
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipBoardSave {

    @NotNull
    private Long bbsUid;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
