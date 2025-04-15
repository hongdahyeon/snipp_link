package hong.snipp.link.snipp_link.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.dto.request
 * fileName       : SnipCommentSave
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipCommentSave {

    @NotNull
    private Long boardUid;

    @NotBlank
    private String content;
}
