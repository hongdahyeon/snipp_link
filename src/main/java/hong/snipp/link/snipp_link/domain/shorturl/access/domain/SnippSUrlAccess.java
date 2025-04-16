package hong.snipp.link.snipp_link.domain.shorturl.access.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.access.domain
 * fileName       : SnippSUrlAccess
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccess -> SUrlAccess
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippSUrlAccess {

    private Long shortenUrlUid;
    private Long userUid;

    /**
     * @method      SnippSUrlAccess 생성자
     * @author      work
     * @date        2025-04-15
     * @deacription 접근 사용자 저장용 생성자
    **/
    public SnippSUrlAccess(Long shortenUrlUid, Long userUid) {
        this.shortenUrlUid = shortenUrlUid;
        this.userUid = userUid;
    }
}
