package hong.snipp.link.snipp_link.domain.shorturl.domain;

import hong.snipp.link.snipp_link.domain.shorturl.dto.request.SnipShortUrlCreate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.domain
 * fileName       : SnipShortUrl
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipShortUrl {

    private Long uid;
    private String originUrl;
    private String shortUrl;
    private String createdAt;
    private String expiresAt;
    private String isPublic;

    /**
     * @method      SnipShortUrl 생성자 1
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 저장용 생성자
    **/
    public SnipShortUrl(SnipShortUrlCreate request) {
        this.originUrl = request.getOriginUrl();
        this.isPublic = request.getIsPublic();
    }

    /**
     * @method      SnipShortUrl 생성자 2
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 수정용 생성자
    **/
    public SnipShortUrl(Long uid, String shortURL) {
        this.uid = uid;
        this.shortUrl = shortURL;
    }
}
