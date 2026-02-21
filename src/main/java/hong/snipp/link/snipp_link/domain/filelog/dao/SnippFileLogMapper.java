package hong.snipp.link.snipp_link.domain.filelog.dao;

import hong.snipp.link.snipp_link.domain.filelog.entity.SnippFileLog;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.filelog.dao
 * fileName       : SnippFileLogMapper
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 로그 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
 */

@Mapper
public interface SnippFileLogMapper extends BaseMapper<SnippFileLog> {
}
