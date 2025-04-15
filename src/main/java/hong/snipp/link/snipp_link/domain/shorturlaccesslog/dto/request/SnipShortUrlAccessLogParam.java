package hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.request
 * fileName       : SnipShortUrlAccessLogParam
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 목록 조회 요청 파라미터 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @Setter
public class SnipShortUrlAccessLogParam {

    private String accessTp;
    private String startDate;
    private String endDate;
}
