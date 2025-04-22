package hong.snipp.link.snipp_link.domain.verifycode.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.verifycode.dto.request
 * fileName       : SnippCheckVerifyCode
 * author         : work
 * date           : 2025-04-22
 * description    : {userEmail}에게 전송된 {verifyCode} 값의 유효성 체크
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippCheckVerifyCode {

    private String userEmail;
    private String verifyCode;
}
