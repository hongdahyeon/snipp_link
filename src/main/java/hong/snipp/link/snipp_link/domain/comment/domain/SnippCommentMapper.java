package hong.snipp.link.snipp_link.domain.comment.domain;

import hong.snipp.link.snipp_link.domain.comment.dto.response.SnippCommentList;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.domain
 * fileName       : SnippCommentMapper
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 */
@Mapper
public interface SnippCommentMapper extends BaseMapper<SnippComment> {

    List<SnippCommentList> getAllCommentList(Long boardUid);
}
