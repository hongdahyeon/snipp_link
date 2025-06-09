package hong.snipp.link.snipp_link.domain.board.service;

import hong.snipp.link.snipp_link.domain.board.domain.SnippBoard;
import hong.snipp.link.snipp_link.domain.board.domain.SnippBoardMapper;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardChange;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardParam;
import hong.snipp.link.snipp_link.domain.board.dto.request.SnippBoardSave;
import hong.snipp.link.snipp_link.domain.board.dto.response.SnippBoardDetail;
import hong.snipp.link.snipp_link.domain.board.dto.response.SnippBoardList;
import hong.snipp.link.snipp_link.domain.board.dto.response.SnippBoardView;
import hong.snipp.link.snipp_link.domain.comment.service.SnippCommentService;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.board.service
 * fileName       : SnippBoardService
 * author         : work
 * date           : 2025-04-15
 * description    : 게시글 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2025-05-30        work       {findBoardCntUseCl} 메소드 추가
 */
@Service
@RequiredArgsConstructor
public class SnippBoardService {

    private final SnippBoardMapper mapper;
    private final SnippCommentService commentService;

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
    public void saveBoard(SnippBoardSave request) {
        mapper.insert(new SnippBoard(request));
    }

    /**
     * @method      changeBoard
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 수정
    **/
    @Transactional
    public void changeBoard(Long uid, SnippBoardChange request) {
        mapper.update(new SnippBoard(uid, request));
    }

    /**
     * @method      findBoardByUid
     * @author      work
     * @date        2025-04-15
     * @deacription 게시글 단건 조회
    **/
    @Transactional(readOnly = true)
    public SnippBoardView findBoardByUid(Long boardUid) {
        SnippBoardView detail = mapper.getDetail(boardUid);
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
    public Page<SnippBoardList> findAllBoardPage(SnippBoardParam param, Pageable pageable) {
        List<SnippBoardList> list = mapper.page(pageable.generateMap(param));
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
    public List<SnippBoardList> findAllBoardList(SnippBoardParam param) {
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
        mapper.delete(new SnippBoard(boardUid));
    }

    /**
     * @method      findBoardCntUseCl
     * @author      dahyeon
     * @date        2025-05-30
     * @deacription {clUid} 분류를 이용하는 게시물 개수 조회
    **/
    @Transactional(readOnly = true)
    public int findBoardCntUseCl(Long clUid) {
        return mapper.countBoardUseCl(clUid);
    }

    /**
     * @method      findDetailOfBoard
     * @author      dahyeon
     * @date        2025-06-09
     * @deacription 게시글 상세 정보 조회
    **/
    @Transactional(readOnly = true)
    public SnippBoardDetail findDetailOfBoard(Long boardUid) {
        return mapper.getDetailOfBoard(boardUid);
    }
}
