package hong.snipp.link.snipp_link.domain.board.domain;

import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.domain
 * fileName       : SnippBoardMapper
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2025-05-30        work       {clUid}를 이용하는 게시글 카운팅
 */
@Mapper
public interface SnippBoardMapper extends BaseMapper<SnippBoard> {

    int countAllBoard(Long bbsUid);

    int countBoardUseCl(Long clUid);
}
