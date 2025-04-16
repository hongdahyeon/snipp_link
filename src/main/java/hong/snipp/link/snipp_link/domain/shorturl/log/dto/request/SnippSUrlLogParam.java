package hong.snipp.link.snipp_link.domain.shorturl.log.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.log.dto.request
 * fileName       : SnippSUrlLogParam
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 목록 조회 요청 파라미터 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccessLog -> SUrlLog
 */
@Getter @Setter
public class SnippSUrlLogParam {

    private String accessTp;
    private String startDate;
    private String endDate;
}
