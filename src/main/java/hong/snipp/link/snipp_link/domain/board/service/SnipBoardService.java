package hong.snipp.link.snipp_link.domain.board.service;

import hong.snipp.link.snipp_link.domain.board.domain.SnipBoard;
import hong.snipp.link.snipp_link.domain.board.domain.SnipBoardMapper;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnipBoardChange;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnipBoardParam;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnipBoardSave;
import hong.snipp.link.snipp_link.domain.board.dto.response.SnipBoardList;
import hong.snipp.link.snipp_link.domain.board.dto.response.SnipBoardView;
import hong.snipp.link.snipp_link.domain.comment.service.SnipCommentService;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.service
 * fileName       : SnipBoardService
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class SnipBoardService {

    private final SnipBoardMapper mapper;
    private final SnipCommentService commentService;

    /**
     * @method      countAllBoard
     * @author      work
     * @date        2025-04-15
     * @deacription {bbsUid} 하위에 게시글 개수 카운팅
    **/
    @Transactional(readOnly = true)
    public int countAllBoard(Long bbsUid) {
        return mapper.countAllBoard(bbsUid);
    }

    /**
     * @method      saveBoard
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 저장
    **/
    @Transactional
    public void saveBoard(SnipBoardSave request) {
        mapper.insert(new SnipBoard(request));
    }

    /**
     * @method      changeBoard
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 수정
    **/
    @Transactional
    public void changeBoard(Long uid, SnipBoardChange request) {
        mapper.update(new SnipBoard(uid, request));
    }

    /**
     * @method      findBoardByUid
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 단건 조회
    **/
    @Transactional(readOnly = true)
    public SnipBoardView findBoardByUid(Long boardUid) {
        SnipBoardView detail = mapper.getDetail(boardUid);
        detail.setComments(commentService.findAllCommentList(boardUid));
        return detail;
    }


    /**
     * @method      findAllBoardPage
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<SnipBoardList> findAllBoardPage(SnipBoardParam param, Pageable pageable) {
        List<SnipBoardList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllBoardList
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<SnipBoardList> findAllBoardList(SnipBoardParam param) {
        return mapper.list(param);
    }

    /**
     * @method      deleteBoard
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 단건 삭제
    **/
    @Transactional
    public void deleteBoard(Long boardUid) {
        mapper.delete(new SnipBoard(boardUid));
    }
}
