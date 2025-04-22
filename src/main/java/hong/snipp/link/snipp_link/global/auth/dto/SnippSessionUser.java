package hong.snipp.link.snipp_link.global.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.global.auth.dto
 * fileName       : SnippSessionUser
 * author         : work
 * date           : 2025-04-16
 * description    : 세션유저로 활용될 VO
 *                  => {userId} 값을 기준으로 비교
 *                  => {callSuper = false} 부모 클래스의 equals(), hashCode() 호출 안하도록
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 * 2025-04-22        work       세션 유저 권한 필드명 : userRole -> role
 */

@Getter
@EqualsAndHashCode(of = "userId", callSuper = false)
public class SnippSessionUser {

    private Long uid;
    private String userId;
    @JsonIgnore
    private String password;
    private String userEmail;
    private String userNm;
    private String role;
    private String lastConnDt;
    private String lastPwdChngDt;
    private int pwdFailCnt;
    private String isLocked;
    private String isEnable;

    private boolean isSocialLogin;
    private Long socialUid;
    private String socialTp;
    private String socialUserId;

    public SnippSessionUser(SnippUserView user) {
        this.uid = user.getUid();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.userEmail = user.getUserEmail();
        this.userNm = user.getUserNm();
        this.role = user.getUserRole();
        this.lastConnDt = user.getLastConnDt();
        this.lastPwdChngDt = user.getLastPwdChngDt();
        this.pwdFailCnt = user.getPwdFailCnt();
        this.isLocked = user.getIsLocked();
        this.isEnable = user.getIsEnable();
        this.isSocialLogin = (user.getSocialUid() != null);
        this.socialUid = user.getSocialUid();
        this.socialTp = user.getSocialTp();
        this.socialUserId = user.getSocialUserId();
    }
}
