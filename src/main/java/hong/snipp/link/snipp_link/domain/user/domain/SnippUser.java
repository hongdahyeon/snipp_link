package hong.snipp.link.snipp_link.domain.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.domain
 * fileName       : SnippUser
 * author         : work
 * date           : 2025-04-15
 * description    : 유저 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippUser {

    private Long uid;
    private String userId;
    private String password;
    private String userEmail;
    private Long socialUid;
}
