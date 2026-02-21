package hong.snipp.link.snipp_link.domain.file.dao;

import hong.snipp.link.snipp_link.domain.file.entity.SnippFile;
import hong.snipp.link.snipp_link.domain.file.dto.response.SnippFileView;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.file.dao
 * fileName       : SnippFileMapper
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 * 2026-02-09        work       deleteFileByFileId 추가
 * 2026-02-22        home       패키지 구조 변경
 */

@Mapper
public interface SnippFileMapper extends BaseMapper<SnippFile> {

    Long generateUid();

    SnippFileView getFileByFileId(String fileId);

    void deleteFileByFileId(String fileId);
}
