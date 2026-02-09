package hong.snipp.link.snipp_link.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.dto.request
 * fileName       : SnippUserInitSave
 * author         : work
 * date           : 2026-02-09
 * description    : 초기 데이터 생성용 요청 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-09        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippUserInitSave {

    @NotBlank
    private String userId;

    @NotBlank
    private String userEmail;

    @NotBlank
    private String password;

    @NotBlank
    private String userNm;

    @NotBlank
    private String role;

    public SnippUserInitSave( String userId, String userEmail, String password, String userNm, String role ) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.password = password;
        this.userNm = userNm;
        this.role = role;
    }
}
