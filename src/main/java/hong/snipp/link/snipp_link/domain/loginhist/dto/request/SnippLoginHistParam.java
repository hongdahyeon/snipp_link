package hong.snipp.link.snipp_link.domain.loginhist.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.loginhist.dto.request
 * fileName       : SnippLoginHistParam
 * author         : work
 * date           : 2025-04-28
 * description    : 로그인 이력 조회 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-28        work       최초 생성
 */
@Getter @Setter
public class SnippLoginHistParam {

    private String loginAccessTp;
    private String startDate;
    private String endDate;
}
