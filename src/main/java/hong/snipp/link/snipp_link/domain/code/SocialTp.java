package hong.snipp.link.snipp_link.domain.code;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.code
 * fileName       : SocialTp
 * author         : work
 * date           : 2025-04-15
 * description    : 소셜 로그인 소셜 유형
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
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
}
