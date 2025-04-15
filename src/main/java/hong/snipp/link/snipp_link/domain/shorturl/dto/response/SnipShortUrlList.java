package hong.snipp.link.snipp_link.domain.shorturl.dto.response;

import hong.snipp.link.snipp_link.domain.shorturlaccess.dto.response.SnipShortUrlAccessList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.dto.response
 * fileName       : SnipShortUrlList
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipShortUrlList {

    private Long uid;
    private String originUrl;
    private String shortUrl;
    private String createdAt;
    private String expiresAt;
    private String isExpired;
    private String isPublic;
    private List<SnipShortUrlAccessList> accessLists = new ArrayList<>();

    public void setAccessLists(List<SnipShortUrlAccessList> accessLists) {
        this.accessLists = accessLists;
    }
}
