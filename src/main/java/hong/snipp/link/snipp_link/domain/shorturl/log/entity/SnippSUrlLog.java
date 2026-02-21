package hong.snipp.link.snipp_link.domain.shorturl.log.entity;

import hong.snipp.link.snipp_link.domain.shorturl.log.dto.request.SnippSUrlLogSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.log.entity
 * fileName       : SnippSUrlLog
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccessLog -> SUrlLog
 * 2026-02-22        home       패키지 구조 변경
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippSUrlLog {

    private Long uid;
    private String shortUrl;
    private Long userUid;
    private String accessIp;
    private String accessUserAgent;
    private String accessDate;
    private String accessTp;

    /**
     * @method      SnippSUrlLog 생성자 1
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 접근 로그 저장
    **/
    public SnippSUrlLog(SnippSUrlLogSave request) {
        this.shortUrl = request.getShortUrl();
        this.userUid = request.getUserUid();
        this.accessIp = request.getAccessIp();
        this.accessUserAgent = request.getAccessUserAgent();
        this.accessTp = request.getAccessTp();
    }
}
