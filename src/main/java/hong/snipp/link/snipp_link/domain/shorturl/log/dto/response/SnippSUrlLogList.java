package hong.snipp.link.snipp_link.domain.shorturl.log.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.log.dto.response
 * fileName       : SnippSUrlLogList
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccessLog -> SUrlLog
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippSUrlLogList {

    private Long logUid;
    private String shortUrl;
    private String originUrl;
    private Long userUid;
    private String accessIp;
    private String accessUserAgent;
    private String accessDate;
    private String accessTp;
}
