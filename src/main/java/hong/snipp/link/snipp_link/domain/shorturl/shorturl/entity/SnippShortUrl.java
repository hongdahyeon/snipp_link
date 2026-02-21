package hong.snipp.link.snipp_link.domain.shorturl.shorturl.entity;

import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.request.SnippShortUrlCreate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.shorturl.entity
 * fileName       : SnippShortUrl
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       파일 이름 변경 : snip -> snipp
 * 2026-02-22        home       패키지 구조 변경
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippShortUrl {

    private Long uid;
    private String originUrl;
    private String shortUrl;
    private String createdAt;
    private String expiresAt;
    private String isPublic;

    /**
     * @method      SnippShortUrl 생성자 1
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 저장용 생성자
    **/
    public SnippShortUrl(SnippShortUrlCreate request) {
        this.originUrl = request.getOriginUrl();
        this.isPublic = request.getIsPublic();
    }

    /**
     * @method      SnippShortUrl 생성자 2
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 수정용 생성자
    **/
    public SnippShortUrl(Long uid, String shortURL) {
        this.uid = uid;
        this.shortUrl = shortURL;
    }
}
