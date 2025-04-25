package hong.snipp.link.snipp_link.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.dto.request
 * fileName       : SnippUserChange
 * author         : work
 * date           : 2025-04-25
 * description    : 유저 수정 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-25        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippUserChange {

    @NotBlank
    private String userEmail;

    private String password;

    @NotBlank
    private String userNm;

}
