package hong.snipp.link.snipp_link.domain.loginhist.dao;

import hong.snipp.link.snipp_link.domain.loginhist.entity.SnippLoginHist;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.loginhist.dao
 * fileName       : SnippLoginHistMapper
 * author         : work
 * date           : 2025-04-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-21        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
 */
@Mapper
public interface SnippLoginHistMapper extends BaseMapper<SnippLoginHist> {
}
