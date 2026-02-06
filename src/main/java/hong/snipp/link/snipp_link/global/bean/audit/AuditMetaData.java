package hong.snipp.link.snipp_link.global.bean.audit;

import lombok.Getter;

import java.util.UUID;

/**
 * packageName    : hong.snipp.link.snipp_link.global.bean.audit
 * fileName       : AuditMetaData
 * author         : work
 * date           : 2025-04-15
 * description    : 작성자 & 수정자 정보 조회
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-05-29        work       필드 추가
 * 2026-02-06        work       mainId 값은 동적 생성
 */

@Getter
public class AuditMetaData {

    private Long regUid;
    private String regDt;
    private String regId;
    private String regNm;
    private Long updtUid;
    private String updtDt;
    private String updtId;
    private String updtNm;

    private String mainId = UUID.randomUUID().toString();

}
