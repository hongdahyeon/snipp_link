package hong.snipp.link.snipp_link.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.dto.request
 * fileName       : SnippUserChangePwd
 * author         : work
 * date           : 2025-04-22
 * description    : 사용자 비밀번호 수정 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippUserChangePwd {

    @NotNull
    private String userId;
    private boolean change;
    private String password;
}
