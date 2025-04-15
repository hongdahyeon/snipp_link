package hong.snipp.link.snipp_link.domain.shorturlaccess.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccess.domain
 * fileName       : SnipShortUrlAccess
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipShortUrlAccess {

    private Long shortenUrlUid;
    private Long userUid;

    /**
     * @method      SnipShortUrlAccess 생성자
     * @author      work
     * @date        2025-04-15
     * @deacription 접근 사용자 저장용 생성자
    **/
    public SnipShortUrlAccess(Long shortenUrlUid, Long userUid) {
        this.shortenUrlUid = shortenUrlUid;
        this.userUid = userUid;
    }
}
