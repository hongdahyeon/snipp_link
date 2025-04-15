package hong.snipp.link.snipp_link.domain.comment.domain;

import hong.snipp.link.snipp_link.domain.comment.dto.response.SnipCommentList;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.domain
 * fileName       : SnipCommentMapper
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Mapper
public interface SnipCommentMapper extends BaseMapper<SnipComment> {

    List<SnipCommentList> getAllCommentList(Long boardUid);
}
