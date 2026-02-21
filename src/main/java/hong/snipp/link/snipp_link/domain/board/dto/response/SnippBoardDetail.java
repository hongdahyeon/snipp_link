package hong.snipp.link.snipp_link.domain.board.dto.response;

import hong.snipp.link.snipp_link.domain.file.dto.response.SnippFileList;
import hong.snipp.link.snipp_link.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.dto.response
 * fileName       : SnippBoardDetail
 * author         : work
 * date           : 2025-06-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-06-09        work       최초 생성
 * 2026-02-08        work       {fileUid} 필드 추가
 * 2026-02-08        work       {files} 필드 추가
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippBoardDetail extends AuditMetaData {

    private Long boardUid;
    private Long bbsUid;
    private Long clUid;
    private String title;
    private String content;
    private String deleteAt;
    private String useAt;
    private Long fileUid;

    private List<SnippFileList> files = new ArrayList<>();

    public void setFiles(List<SnippFileList> files) {
        this. files = files;
    }
}
