package hong.snipp.link.snipp_link.domain.loginhist.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.loginhist.dto.request
 * fileName       : SnippLoginHistSave
 * author         : work
 * date           : 2025-04-21
 * description    : 로그인 이력 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-21        work       최초 생성
 * 2025-04-22        work       {loginAccessTp, loginAccessDescription} 필드 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippLoginHistSave {

    private String userEmail;
    private String accessIp;
    private String accessUserAgent;
    private String loginAccessTp;
    private String loginAccessDescription;

    @Builder(builderMethodName = "insertLoginHist")
    public SnippLoginHistSave(String userEmail, String accessIp,
                              String accessUserAgent, String loginAccessTp, String loginAccessDescription) {
        this.userEmail = userEmail;
        this.accessIp = accessIp;
        this.accessUserAgent = accessUserAgent;
        this.loginAccessTp = loginAccessTp;
        this.loginAccessDescription = loginAccessDescription;
    }
}
