package hong.snipp.link.snipp_link.domain.bbs.dto.request;

import hong.snipp.link.snipp_link.global.annotation.YorN;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.dto.request
 * fileName       : SnipBbsParam
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 목록 조회 요청 파라미터 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter @Setter
public class SnipBbsParam {

    private String bbsNm;

    private String bbsTp;

    @YorN
    private String useAt;
}
