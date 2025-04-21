package hong.snipp.link.snipp_link.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.dto.response
 * fileName       : SnippUserView
 * author         : work
 * date           : 2025-04-16
 * description    : DB 유저 조회 -> 세션 유저에 담기 위한 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 * 2025-04-21        work       access-level 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippUserView {

    private Long uid;
    private String userId;
    private String password;
    private String userEmail;
    private String userNm;
    private String userRole;
    private String lastConnDt;
    private String lastPwdChngDt;
    private int pwdFailCnt;
    private String isLocked;
    private String isEnable;

    private Long socialUid;
    private String socialTp;
    private String socialUserId;
}
