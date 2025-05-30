package hong.snipp.link.snipp_link.domain.bbscl.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.dto.response
 * fileName       : SnippBbsClList
 * author         : work
 * date           : 2025-05-30
 * description    : 분류 목록을 리스트 형태 조회
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-30        work       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBbsClList {

    private Long no;
    private Long upperNo;
    private String text;
    private Long sortNo;
    private Long bbsUid;
    private String bbsNm;
    private String path;
    private String depth;
}
