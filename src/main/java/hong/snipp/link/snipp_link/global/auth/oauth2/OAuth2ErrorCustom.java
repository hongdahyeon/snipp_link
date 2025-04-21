package hong.snipp.link.snipp_link.global.auth.oauth2;

import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * packageName    : hong.snipp.link.snipp_link.global.auth.oauth2
 * fileName       : OAuth2ErrorCustom
 * author         : work
 * date           : 2025-04-18
 * description    : 소셜 로그인 에러 리턴 커스텀
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 */
public class OAuth2ErrorCustom extends OAuth2Error {

    private OAuth2ErrorCode oAuth2ErrorCode;
    private String userId;
    private String userEmail;

    public OAuth2ErrorCustom(String errorCode, String description, String uri) {
        super(errorCode, description, uri);
    }

    public OAuth2ErrorCustom(String errorCode) {
        super(errorCode);
    }

    public OAuth2ErrorCustom(OAuth2ErrorCode oAuth2ErrorCode, String userEmail, String userId) {
        super(oAuth2ErrorCode.name());
        this.oAuth2ErrorCode = oAuth2ErrorCode;
        this.userEmail = userEmail;
        this.userId = userId;
    }

    public OAuth2ErrorCode getOAuth2ErrorCode() {
        return this.oAuth2ErrorCode;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getUserId() {
        return this.userId;
    }
}
