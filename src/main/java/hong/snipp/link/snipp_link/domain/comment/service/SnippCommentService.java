package hong.snipp.link.snipp_link.domain.comment.service;

import hong.snipp.link.snipp_link.domain.comment.entity.SnippComment;
import hong.snipp.link.snipp_link.domain.comment.dao.SnippCommentMapper;
import hong.snipp.link.snipp_link.domain.comment.dto.request.SnippCommentChange;
import hong.snipp.link.snipp_link.domain.comment.dto.request.SnippCommentSave;
import hong.snipp.link.snipp_link.domain.comment.dto.response.SnippCommentList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.service
 * fileName       : SnippCommentService
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 관련 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 */
@Service
@RequiredArgsConstructor
public class SnippCommentService {

    private final SnippCommentMapper mapper;

    /**
     * @method      saveComment
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 저장
    **/
    @Transactional
    public void saveComment(SnippCommentSave request) {
        mapper.insert(new SnippComment(request));
    }

    /**
     * @method      changeComment
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 수정
    **/
    @Transactional
    public void changeComment(Long uid, SnippCommentChange request) {
        mapper.update(new SnippComment(uid, request));
    }

    /**
     * @method      findAllCommentList
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 하위 댓글 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<SnippCommentList> findAllCommentList(Long boardUid) {
        return mapper.getAllCommentList(boardUid);
    }

    /**
     * @method      deleteComment
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 삭제
    **/
    @Transactional
    public void deleteComment(Long commentUid) {
        mapper.delete(new SnippComment(commentUid));
    }
}
