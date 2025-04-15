package hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.request
 * fileName       : SnipShortUrlAccessLogSave
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipShortUrlAccessLogSave {

    private String shortUrl;
    private Long userUid;
    private String accessIp;
    private String accessUserAgent;
    private String accessTp;

    @Builder(builderMethodName = "insertShortURLAccessLog")
    public SnipShortUrlAccessLogSave(String shortUrl, Long userUid, String accessIp, String accessUserAgent, String accessTp) {
        this.shortUrl = shortUrl;
        this.userUid = userUid;
        this.accessIp = accessIp;
        this.accessUserAgent = accessUserAgent;
        this.accessTp = accessTp;
    }
}
