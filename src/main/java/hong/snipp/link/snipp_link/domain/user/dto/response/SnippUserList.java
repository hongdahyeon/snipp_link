package hong.snipp.link.snipp_link.domain.user.dto.response;

import hong.snipp.link.snipp_link.global.bean.ResponseIdBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.dto.response
 * fileName       : SnippUserList
 * author         : work
 * date           : 2025-04-23
 * description    : 유저 목록 응답용 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-23        work       최초 생성
 * 2025-04-25        work       ResponseIdBean extend 받기
 * 2026-01-12        home       setter user_role_kr
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippUserList extends ResponseIdBean {

    private Long uid;
    private String userId;
    private String password;
    private String userEmail;
    private String userNm;
    private String userRole;
    private String userRoleKr;
    private String lastConnDt;
    private String lastPwdChngDt;
    private int pwdFailCnt;
    private String isLocked;
    private String isEnable;

    private Long socialUid;
    private String socialTp;
    private String socialTpKor;
    private String socialUserId;

    public void setUserRoleKr(String roleKr) {
        this.userRoleKr = roleKr;
    }

    public void setSocialTpKor(String socialTpKor) {
        this.socialTpKor = socialTpKor;
    }
}
