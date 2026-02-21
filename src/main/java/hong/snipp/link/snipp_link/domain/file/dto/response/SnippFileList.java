package hong.snipp.link.snipp_link.domain.file.dto.response;

import hong.snipp.link.snipp_link.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.file.dto.response
 * fileName       : SnippFileList
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 목록 조회 응답 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 */


@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippFileList extends AuditMetaData {

    private Long uid;
    private String fileId;
    private String fileNm;
    private String orgFileNm;
    private String fileUrl;
    private String filePath;
    private String fileType;
    private String extension;
    private String storageTp;
    private Long fileSize;
    private Integer downCnt;
    private String deleteAt;
}
