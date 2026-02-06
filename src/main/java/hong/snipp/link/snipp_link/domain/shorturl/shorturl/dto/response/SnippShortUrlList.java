package hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response;

import hong.snipp.link.snipp_link.domain.shorturl.access.dto.response.SnippSUrlAccessList;
import hong.snipp.link.snipp_link.domain.shorturl.log.dto.response.SnippSUrlLogList;
import hong.snipp.link.snipp_link.global.bean.ResponseIdBean;
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
 * 2026-01-14        work       logLists 필드 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippShortUrlList extends ResponseIdBean {

    private Long uid;
    private String originUrl;
    private String shortUrl;
    private String createdAt;
    private String expiresAt;
    private String isExpired;
    private String isPublic;
    private List<SnippSUrlAccessList> accessLists = new ArrayList<>();
    private List<SnippSUrlLogList> logLists = new ArrayList<>();

    public void setAccessLists(List<SnippSUrlAccessList> accessLists) {
        this.accessLists = accessLists;
    }

    public void setLogLists(List<SnippSUrlLogList> logLists) {
        this.logLists = logLists;
    }
}
