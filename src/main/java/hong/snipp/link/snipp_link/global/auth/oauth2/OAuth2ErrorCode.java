package hong.snipp.link.snipp_link.global.auth.oauth2;

/**
 * packageName    : hong.snipp.link.snipp_link.global.auth.oauth2
 * fileName       : OAuth2ErrorCode
 * author         : work
 * date           : 2025-04-18
 * description    : 소셜 로그인 관련 에러 코드
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 */
public enum OAuth2ErrorCode {

    socialEmailDuplicate("이메일이 중복됩니다. \n 다른 이메일로 로그인 해주세요.", "socialEmailDuplicate"),
    socialEnable("계정이 비활성화 되었습니다. \n 관리자에게 문의 바랍니다.", "socialEnable"),
    socialLock("관리자에 의해 계정이 잠겼습니다. \n 관리자에게 문의해주세요.", "socialLock"),
    socialExpired("최근 로그인일로부터 1년이 지나 \n  휴먼 계정이 되었습니다. \n 이메일 인증을 해주세요.", "socialExpired"),
    socialError("소셜 로그인 과정에서 오류가 발생했습니다. \n 관리자에게 문의해주세요.", "socialError");

    public String message;
    public String type;

    OAuth2ErrorCode(String message, String type) {
        this.message = message;
        this.type = type;
    }
}
