package hong.snipp.link.snipp_link.domain.board.domain;

import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.domain
 * fileName       : SnipBoardMapper
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Mapper
public interface SnipBoardMapper extends BaseMapper<SnipBoard> {

    int countAllBoard(Long bbsUid);
}
