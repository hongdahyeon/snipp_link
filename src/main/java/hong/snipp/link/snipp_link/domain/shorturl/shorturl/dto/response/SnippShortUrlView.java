package hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response
 * fileName       : SnippShortUrlView
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 단건 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       파일 이름 변경 : snip -> snipp
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippShortUrlView {

    private Long uid;
    private String originUrl;
    private String shortUrl;
    private String createdAt;
    private String expiresAt;
    private String isExpired;
    private String isPublic;
}
