package hong.snipp.link.snipp_link.domain.loginhist.entity;

import hong.snipp.link.snipp_link.domain.loginhist.dto.request.SnippLoginHistSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.loginhist.entity
 * fileName       : SnippLoginHist
 * author         : work
 * date           : 2025-04-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-21        work       최초 생성
 * 2025-04-22        work       {loginAccessTp, loginAccessDescription} 필드 추가
 * 2026-02-22        home       패키지 구조 변경
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippLoginHist {

    private Long uid;
    private String userEmail;
    private String loginDt;
    private String accessIp;
    private String accessUserAgent;
    private String loginAccessTp;
    private String loginAccessDescription;

    /**
     * @method      SnippLoginHist 생성자 1
     * @author      work
     * @date        2025-04-21
     * @deacription 로그인 이력 저장
    **/
    public SnippLoginHist(SnippLoginHistSave request) {
        this.userEmail = request.getUserEmail();
        this.accessIp = request.getAccessIp();
        this.accessUserAgent = request.getAccessUserAgent();
        this.loginAccessTp = request.getLoginAccessTp();
        this.loginAccessDescription = request.getLoginAccessDescription();
    }
}
