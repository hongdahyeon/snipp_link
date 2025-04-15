package hong.snipp.link.snipp_link.domain.shorturlaccesslog.domain;

import hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.request.SnipShortUrlAccessLogSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccesslog.domain
 * fileName       : SnipShortUrlAccessLog
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipShortUrlAccessLog {

    private Long uid;
    private String shortUrl;
    private Long userUid;
    private String accessIp;
    private String accessUserAgent;
    private String accessDate;
    private String accessTp;

    /**
     * @method      SnipShortUrlAccessLog 생성자 1
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 접근 로그 저장
    **/
    public SnipShortUrlAccessLog(SnipShortUrlAccessLogSave request) {
        this.shortUrl = request.getShortUrl();
        this.userUid = request.getUserUid();
        this.accessIp = request.getAccessIp();
        this.accessUserAgent = request.getAccessUserAgent();
        this.accessTp = request.getAccessTp();
    }
}
