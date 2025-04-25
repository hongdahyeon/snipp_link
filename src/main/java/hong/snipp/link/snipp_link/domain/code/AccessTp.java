package hong.snipp.link.snipp_link.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.code
 * fileName       : AccessTp
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT_URL 접근 성공 여부
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-25        work       isSuccess 추가
 */
@Getter
public enum AccessTp {

    ACCESS_SUCCESS("성공", true),
    ACCESS_NO_PERMISSION("접근 권한 없음", false),
    ACCESS_NOT_FOUND("URL 없음", false),
    ACCESS_TIME_EXPIRED("유효 기간 만료", false);

    private String description;
    private boolean isSuccess;

    AccessTp(String description, boolean isSuccess) {
        this.description = description;
        this.isSuccess = isSuccess;
    }

    public static boolean isValidCode(String code) {
        for( AccessTp accessTp : AccessTp.values() ) {
            if(accessTp.name().equals(code)) {
                return true;
            }
        }
        return  false;
    }

    public static AccessTp getAccessTp(String code) {
        for( AccessTp accessTp : AccessTp.values() ) {
            if(accessTp.name().equals(code)) {
                return accessTp;
            }
        }
        return  null;
    }
}
