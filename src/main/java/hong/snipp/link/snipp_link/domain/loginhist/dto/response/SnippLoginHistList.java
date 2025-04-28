package hong.snipp.link.snipp_link.domain.loginhist.dto.response;

import hong.snipp.link.snipp_link.domain.code.LoginTp;
import hong.snipp.link.snipp_link.global.bean.ResponseIdBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.loginhist.dto.response
 * fileName       : SnippLoginHistList
 * author         : work
 * date           : 2025-04-28
 * description    : 로그인 이력 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-28        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippLoginHistList extends ResponseIdBean {

    private Long histUid;
    private String userEmail;
    private String loginDt;
    private String accessIp;
    private String accessUserAgent;
    private String loginAccessTp;
    private String loginAccessTpStr;
    private boolean loginAccessTpIsSuccess;
    private String loginAccessDescription;

    public void setLoginAccessTp(String loginAccessTp) {
        LoginTp tp = LoginTp.getLoginAccessTp(loginAccessTp);
        if(tp != null) {
            this.loginAccessTp = tp.name();
            this.loginAccessTpStr = tp.getDescription();
            this.loginAccessTpIsSuccess = tp.isSuccess();
        }
    }
}
