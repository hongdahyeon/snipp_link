package hong.snipp.link.snipp_link.domain.bbs.dto.request;

import hong.snipp.link.snipp_link.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.dto.request
 * fileName       : SnippBbsChange
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 수정 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBbsChange {

    @NotBlank
    private String bbsNm;

    @YorN(allowNull = false)
    private String useAt;
}
