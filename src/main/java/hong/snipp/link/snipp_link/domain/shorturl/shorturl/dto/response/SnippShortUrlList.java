package hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response;

import hong.snipp.link.snipp_link.domain.shorturl.access.dto.response.SnippSUrlAccessList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response
 * fileName       : SnippShortUrlList
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccess -> SUrlAccess
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippShortUrlList {

    private Long uid;
    private String originUrl;
    private String shortUrl;
    private String createdAt;
    private String expiresAt;
    private String isExpired;
    private String isPublic;
    private List<SnippSUrlAccessList> accessLists = new ArrayList<>();

    public void setAccessLists(List<SnippSUrlAccessList> accessLists) {
        this.accessLists = accessLists;
    }
}
