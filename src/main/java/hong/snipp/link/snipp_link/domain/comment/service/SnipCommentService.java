package hong.snipp.link.snipp_link.domain.comment.service;

import hong.snipp.link.snipp_link.domain.comment.domain.SnipComment;
import hong.snipp.link.snipp_link.domain.comment.domain.SnipCommentMapper;
import hong.snipp.link.snipp_link.domain.comment.dto.request.SnipCommentChange;
import hong.snipp.link.snipp_link.domain.comment.dto.request.SnipCommentSave;
import hong.snipp.link.snipp_link.domain.comment.dto.response.SnipCommentList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.comment.service
 * fileName       : SnipCommentService
 * author         : work
 * date           : 2025-04-15
 * description    : 댓글 관련 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class SnipCommentService {

    private final SnipCommentMapper mapper;

    /**
     * @method      saveComment
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 저장
    **/
    @Transactional
    public void saveComment(SnipCommentSave request) {
        mapper.insert(new SnipComment(request));
    }

    /**
     * @method      changeComment
     * @author      work
     * @date        2025-04-15
     * @deacription 댓글 수정
    **/
    @Transactional
    public void changeComment(Long uid, SnipCommentChange request) {
        mapper.update(new SnipComment(uid, request));
    }

    /**
     * @method      findAllCommentList
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 하위 댓글 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<SnipCommentList> findAllCommentList(Long boardUid) {
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
        mapper.delete(new SnipComment(commentUid));
    }
}
