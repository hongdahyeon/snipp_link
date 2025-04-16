package hong.snipp.link.snipp_link.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.dto.request
 * fileName       : SnippCommentChange
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 수정 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippCommentChange {

    @NotBlank
    private String content;
}
