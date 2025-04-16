package hong.snipp.link.snipp_link.domain.shorturl.access.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.access.dto.response
 * fileName       : SnippSUrlAccessList
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 권한 사용자 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccess -> SUrlAccess
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippSUrlAccessList {

    private Long shortenUrlUid;
    private Long userUid;
    private String userId;
}
