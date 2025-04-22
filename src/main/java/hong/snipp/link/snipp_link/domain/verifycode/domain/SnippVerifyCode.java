package hong.snipp.link.snipp_link.domain.verifycode.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.verifycode.domain
 * fileName       : SnippVerifyCode
 * author         : work
 * date           : 2025-04-22
 * description    : 유저에게 발송된 인증 코드 관련 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippVerifyCode {

    private Long uid;
    private String verifyCode;
    private String userEmail;
    private String isExpired;
    private String createdAt;

    /**
     * @method      SnippVerifyCode 생성자 1
     * @author      work
     * @date        2025-04-22
     * @deacription 이메일로 발송한 인증번호 저장
    **/
    public SnippVerifyCode(String userEmail, String verifyCode) {
        this.userEmail = userEmail;
        this.verifyCode = verifyCode;
    }
}
