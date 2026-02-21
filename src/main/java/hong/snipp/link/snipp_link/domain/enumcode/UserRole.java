package hong.snipp.link.snipp_link.domain.enumcode;

import lombok.Getter;

import java.util.Arrays;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.enumcode
 * fileName       : UserRole
 * author         : work
 * date           : 2025-04-16
 * description    : 유저 권한
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 * 2025-04-21        work       SUPER -> ROLE_SUPER 이름 수정
 * 2025-01-12        home       code -> name
 * 2026-02-09        work       initData 필드 추가
 * 2026-02-22        home       패키지 구조 변경
 */
@Getter
public enum UserRole {

    ROLE_USER("일반 사용자 권한", "user"),
    ROLE_MANAGER("매니저 권한", "manager"),
    ROLE_SUPER("슈퍼 권한", "super");

    private String name;
    private String initData;

    UserRole(String name, String initData) {
        this.name = name;
        this.initData = initData;
    }

    public static boolean isValidCode(String role) {
        for( UserRole userRole : UserRole.values() ) {
            if(userRole.name().equals(role)) {
                return true;
            }
        }
        return  false;
    }

    /**
     * Code(Enum 상수명) -> Name(설명) 변환
     * 예: "ROLE_USER" -> "일반 사용자 권한"
     */
    public static String getNameByCode(String code) {
        if (code == null) return "";
        return Arrays.stream(UserRole.values())
                .filter(role -> role.name().equals(code))
                .findFirst()
                .map(UserRole::getName)
                .orElse("");
    }
}
