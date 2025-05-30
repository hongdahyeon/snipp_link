package hong.snipp.link.snipp_link.domain.bbscl.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.dto.request
 * fileName       : SnippBbsClSave
 * author         : work
 * date           : 2025-05-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-30        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBbsClSave {

    @NotNull
    private Long bbsUid;

    @NotBlank
    private String clNm;

    @NotNull
    private Long sortNo;

    @NotNull
    private Long upperCl;
}
