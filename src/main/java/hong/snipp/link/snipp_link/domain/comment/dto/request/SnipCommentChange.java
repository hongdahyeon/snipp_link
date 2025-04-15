package hong.snipp.link.snipp_link.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.dto.request
 * fileName       : SnipCommentChange
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 수정 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipCommentChange {

    @NotBlank
    private String content;
}
