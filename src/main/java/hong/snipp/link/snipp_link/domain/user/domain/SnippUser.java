package hong.snipp.link.snipp_link.domain.user.domain;

import hong.snipp.link.snipp_link.domain.code.UserRole;
import hong.snipp.link.snipp_link.domain.socialuser.dto.request.SnippOAuth2UserSave;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserSave;
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
 * 2025-04-16        work       필드 추가
 *                              : userNm , userRole ,  lastConnDt , lastPwdChngDt , pwdFailCnt , isLocked , isEnable
 * 2025-04-21        work       소셜/폼 로그인 유저 저장용 생성자 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippUser {

    private Long uid;
    private String userId;
    private String password;
    private String userEmail;
    private String userNm;
    private Long socialUid;
    private String userRole;
    private String lastConnDt;
    private String lastPwdChngDt;
    private int pwdFailCnt;
    private String isLocked;
    private String isEnable;

    /**
     * @method      SnippUser 생성자 1
     * @author      work
     * @date        2025-04-21
     * @deacription 소셜 로그인 유저 저장용 생성자
    **/
    public SnippUser(SnippOAuth2UserSave request, String encodePassword, Long socialUid) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userEmail = request.getUserEmail();
        this.userNm = request.getUserEmail();
        this.userRole = UserRole.ROLE_USER.name();
        this.socialUid = socialUid;
    }

    /**
     * @method      SnippUser 생성자 2
     * @author      work
     * @date        2025-04-21
     * @deacription 폼 로그인 유저 저장용 생성자
    **/
    public SnippUser(SnippUserSave request, String encodePassword) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userEmail = request.getUserEmail();
        this.userNm = request.getUserNm();
        this.userRole = UserRole.ROLE_USER.name();
    }
}
