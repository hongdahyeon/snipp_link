package hong.snipp.link.snipp_link.domain.file.dto.request;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.file.dto.request
 * fileName       : SnippFileUpload
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 업로드 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 */

@Getter
public class SnippFileUpload {

    private String fileId;
    private String fileNm;
    private String orgFileNm;
    private String fileUrl;
    private String filePath;
    private String fileType;
    private String extension;
    private String storageTp;
    private Long fileSize;

    @Builder
    public SnippFileUpload( String fileId, String fileNm, String orgFileNm, String fileUrl,
                            String filePath, String fileType, String extension, String storageTp, Long fileSize ) {
        this.fileId = fileId;
        this.fileNm = fileNm;
        this.orgFileNm = orgFileNm;
        this.fileUrl = fileUrl;
        this.filePath = filePath;
        this.fileType = fileType;
        this.extension = extension;
        this.storageTp = storageTp;
        this.fileSize = fileSize;
    }
}
