package hong.snipp.link.snipp_link.domain.file.entity;

import hong.snipp.link.snipp_link.domain.file.dto.request.SnippFileUpload;
import hong.snipp.link.snipp_link.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.file.entity
 * fileName       : SnippFile
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippFile extends AuditBean {

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

    public SnippFile( Long fileUid, SnippFileUpload uploadDto ) {
        this.uid = fileUid;
        this.fileId = uploadDto.getFileId();
        this.fileNm = uploadDto.getFileNm();
        this.orgFileNm = uploadDto.getOrgFileNm();
        this.fileUrl = uploadDto.getFileUrl();
        this.filePath = uploadDto.getFilePath();
        this.fileType = uploadDto.getFileType();
        this.extension = uploadDto.getExtension();
        this.storageTp = uploadDto.getStorageTp();
        this.fileSize = uploadDto.getFileSize();
    }
}
