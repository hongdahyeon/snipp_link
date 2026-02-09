package hong.snipp.link.snipp_link.domain.board.dto.request;

import hong.snipp.link.snipp_link.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.dto.request
 * fileName       : SnippBoardChange
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 수정 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2026-02-08        work       {fileUid} 필드 추가
 * 2026-02-09        work       {deleteFiles} 필드 추가
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBoardChange {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @YorN
    private String useAt;

    private Long fileUid;

    private List<String> deleteFiles;
}
