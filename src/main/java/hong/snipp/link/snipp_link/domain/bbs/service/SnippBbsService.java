package hong.snipp.link.snipp_link.domain.bbs.service;

import hong.snipp.link.snipp_link.domain.bbs.domain.SnippBbs;
import hong.snipp.link.snipp_link.domain.bbs.domain.SnippBbsMapper;
import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnippBbsChange;
import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnippBbsParam;
import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnippBbsSave;
import hong.snipp.link.snipp_link.domain.bbs.dto.response.SnippBbsList;
import hong.snipp.link.snipp_link.domain.bbs.dto.response.SnippBbsView;
import hong.snipp.link.snipp_link.domain.board.service.SnippBoardService;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import hong.snipp.link.snipp_link.global.exception.SnippException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbs.service
 * fileName       : SnippBbsService
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       snip -> snipp 변경
 * 2025-04-25        work       private -> private final
 */
@Service
@RequiredArgsConstructor
public class SnippBbsService {

    private final SnippBbsMapper mapper;
    private final SnippBoardService boardService;

    /**
     * @method      saveBbs
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 저장
    **/
    @Transactional
    public void saveBbs(SnippBbsSave request) {
        mapper.insert(new SnippBbs(request));
    }

    /**
     * @method      changeBbs
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 수정
    **/
    @Transactional
    public void changeBbs(Long uid, SnippBbsChange request) {
        mapper.update(new SnippBbs(uid, request));
    }

    /**
     * @method      findBbsByUid
     * @author      work
     * @date        2025-04-15
     * @deacription {uid} 값으로 게시판 단건 조회
    **/
    @Transactional(readOnly = true)
    public SnippBbsView findBbsByUid(Long uid) {
        return mapper.getDetail(uid);
    }

    /**
     * @method      findAllBbsPage
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<SnippBbsList> findAllBbsPage(SnippBbsParam param, Pageable pageable) {
        List<SnippBbsList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllBbsList
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<SnippBbsList> findAllBbsList(SnippBbsParam param) {
        return mapper.list(param);
    }

    /**
     * @method      deleteBbs
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 단건 삭제
    **/
    @Transactional
    public void deleteBbs(Long bbsUid) {
        int count = boardService.countAllBoard(bbsUid);
        if(count != 0) {
            throw new SnippException(
                    bbsUid + "번 게시판 하위에 게시글이 존재하여 삭제가 불가능합니다.",
                    HttpStatus.BAD_REQUEST);
        }
        mapper.delete(new SnippBbs(bbsUid));
    }

}
