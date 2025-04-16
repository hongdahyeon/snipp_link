package hong.snipp.link.snipp_link.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.code
 * fileName       : UserRole
 * author         : work
 * date           : 2025-04-16
 * description    : 유저 권한
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 */
@Getter
public enum UserRole {

    ROLE_USER("일반 사용자 권한"),
    ROLE_MANAGER("매니저 권한"),
    SUPER("슈퍼 권한");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public static boolean isValidCode(String role) {
        for( UserRole userRole : UserRole.values() ) {
            if(userRole.name().equals(role)) {
                return true;
            }
        }
        return  false;
    }
}
