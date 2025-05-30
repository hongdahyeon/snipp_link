package hong.snipp.link.snipp_link.domain.bbs.domain;

import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.domain
 * fileName       : SnippBbsMapper
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2025-05-29        work       {getRecentBbs} 추가
 */
@Mapper
public interface SnippBbsMapper extends BaseMapper<SnippBbs> {

    SnippBbs getRecentBbs(String bbsTp);
}
