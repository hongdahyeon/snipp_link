package hong.snipp.link.snipp_link.domain.enumcode;

import lombok.Getter;

import java.util.Arrays;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.enumcode
 * fileName       : SocialTp
 * author         : work
 * date           : 2025-04-15
 * description    : 소셜 로그인 소셜 유형
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2026-01-12        home       code -> name, getter 추가
 * 2026-02-22        home       패키지 구조 변경
 */
@Getter
public enum SocialTp {

    SOCIAL_KAKAO("카카오"),
    SOCIAL_NAVER("네이버"),
    SOCIAL_GOOGLE("구글");

    private String name;

    SocialTp(String name) {
        this.name = name;
    }

    public static boolean isValidCode(String code) {
        for( SocialTp socialTp : SocialTp.values() ) {
            if(socialTp.name().equals(code)) {
                return true;
            }
        }
        return  false;
    }

    /**
     * Code(Enum 상수명) -> Name(설명) 변환
     */
    public static String getNameByCode(String code) {
        if (code == null) return "";
        return Arrays.stream(SocialTp.values())
                .filter(role -> role.name().equals(code))
                .findFirst()
                .map(SocialTp::getName)
                .orElse("");
    }
}
