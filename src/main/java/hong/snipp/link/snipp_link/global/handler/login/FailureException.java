package hong.snipp.link.snipp_link.global.handler.login;

/**
 * packageName    : hong.snipp.link.snipp_link.global.handler.login
 * fileName       : FailureException
 * author         : work
 * date           : 2025-04-18
 * description    : 로그인 실패 exception 값에 대한 메시지 & type
 *                  > message : 유저에게 뿌려줄 메시지 정보
 *                  > type : 각각의 값에 따라 서비스 로직을 다르게 타게 된다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 * 2025-04-22        work       비밀번호 5회 실패시 출력 문구 수정
 */


public enum FailureException {

    PASSWORD_FAIL_5_TIMES("비밀번호 5회 오류로 계정이 잠겼습니다.\n관리자에게 문의 바랍니다.", "error"),
    PASSWORD_FAIL("비밀번호 %d / 5 회 오류", "error"),
    UsernameNotFoundException("등록되지 않은 사용자입니다. \n 회원가입을 먼저 해주시기 바랍니다.", "none"),
    DisabledException("계정이 비활성화 되었습니다. \n 관리자에게 문의 바랍니다.", "disable"),
    CredentialsExpiredException("비밀번호가 만료되었습니다. \n 비밀번호를 변경해주세요.", "expired"),
    AccountExpiredException("최근 로그인일로부터 1년이 지나 \n  휴먼 계정이 되었습니다. \n 이메일 인증을 해주세요.", "account"),
    InternalAuthenticationServiceException("내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. \n 관리자에게 문의해주세요.", "error"),
    DuplicateLogin("이미 접속 중인 아이디입니다. \n 다시 로그인하시겠습니까?", "session"),
    SessionExpired("세션이 만료되었습니다.", "sessionexpired"),
    LockedException("비밀번호 5회 오류로 계정이 잠겼습니다. \n 관리자에게 문의해주세요.", "error");

    public String message;
    public String type;

    FailureException(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage(int failCnt) {
        return String.format(message, failCnt);
    }
}
