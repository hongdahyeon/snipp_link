package hong.snipp.link.snipp_link.domain.socialuser.entity;

import hong.snipp.link.snipp_link.domain.socialuser.dto.request.SnippOAuth2UserSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.socialuser.entity
 * fileName       : SnippSocialUser
 * author         : work
 * date           : 2025-04-15
 * description    : 소셜 유저 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-21        work       소셜 유저 저장용 생성자 추가
 * 2026-02-22        home       패키지 구조 변경
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippSocialUser {

    private Long uid;
    private String socialTp;
    private String userId;

    /**
     * @method      SnippSocialUser 생성자 1
     * @author      work
     * @date        2025-04-18
     * @deacription 소셜 유저 저장용 생성자
    **/
    public SnippSocialUser(SnippOAuth2UserSave request) {
        this.socialTp = request.getSocialTp();
        this.userId = request.getUserId();
    }
}
