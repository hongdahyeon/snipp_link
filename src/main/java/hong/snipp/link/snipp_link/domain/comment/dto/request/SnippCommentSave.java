package hong.snipp.link.snipp_link.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.dto.request
 * fileName       : SnippCommentSave
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippCommentSave {

    @NotNull
    private Long boardUid;

    @NotBlank
    private String content;
}
