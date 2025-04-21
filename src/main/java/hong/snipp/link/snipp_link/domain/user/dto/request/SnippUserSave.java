package hong.snipp.link.snipp_link.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.dto.request
 * fileName       : SnippUserSave
 * author         : work
 * date           : 2025-04-21
 * description    : 유저 저장용 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-21        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippUserSave {

    @NotBlank
    private String userId;

    @NotBlank
    private String userEmail;

    @NotBlank
    private String password;

    @NotBlank
    private String userNm;

}
