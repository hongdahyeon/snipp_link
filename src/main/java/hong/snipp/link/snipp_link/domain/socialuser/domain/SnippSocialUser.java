package hong.snipp.link.snipp_link.domain.socialuser.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.socialuser.domain
 * fileName       : SnippSocialUser
 * author         : work
 * date           : 2025-04-15
 * description    : 소셜 유저 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippSocialUser {

    private Long uid;
    private String socialTp;
    private String userId;
}
