package hong.snipp.link.snipp_link.domain.enumcode;

import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.enumode
 * fileName       : LoginTp
 * author         : work
 * date           : 2025-04-18
 * description    : 로그인 성공/실패 코드
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 * 2025-04-28        work       {isSuccess} 추가
 * 2026-02-22        home       패키지 구조 변경
 */
@Getter
public enum LoginTp {

    LOGIN_SUCCESS("로그인 성공", true),
    LOGIN_FAIL("로그인 실패", false);

    private String description;
    private boolean isSuccess;

    LoginTp(String description, boolean isSuccess) {
        this.description = description;
        this.isSuccess = isSuccess;
    }

    public static LoginTp getLoginAccessTp(String code) {
        for( LoginTp loginTp : LoginTp.values() ) {
            if(loginTp.name().equals(code)) {
                return loginTp;
            }
        }
        return  null;
    }
}
