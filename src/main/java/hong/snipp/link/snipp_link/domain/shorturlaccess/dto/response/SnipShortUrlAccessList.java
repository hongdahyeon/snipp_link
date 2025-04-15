package hong.snipp.link.snipp_link.domain.shorturlaccess.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccess.dto.response
 * fileName       : SnipShortUrlAccessList
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 권한 사용자 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipShortUrlAccessList {

    private Long shortenUrlUid;
    private Long userUid;
    private String userId;
}
