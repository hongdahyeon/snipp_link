package hong.snipp.link.snipp_link.domain.bbs.service;

import hong.snipp.link.snipp_link.domain.bbs.domain.SnipBbs;
import hong.snipp.link.snipp_link.domain.bbs.domain.SnipBbsMapper;
import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnipBbsChange;
import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnipBbsParam;
import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnipBbsSave;
import hong.snipp.link.snipp_link.domain.bbs.dto.response.SnipBbsList;
import hong.snipp.link.snipp_link.domain.bbs.dto.response.SnipBbsView;
import hong.snipp.link.snipp_link.domain.board.service.SnipBoardService;
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
 * fileName       : SnipBbsService
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class SnipBbsService {

    private SnipBbsMapper mapper;
    private SnipBoardService boardService;

    /**
     * @method      saveBbs
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 저장
    **/
    @Transactional
    public void saveBbs(SnipBbsSave request) {
        mapper.insert(new SnipBbs(request));
    }

    /**
     * @method      changeBbs
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 수정
    **/
    @Transactional
    public void changeBbs(Long uid, SnipBbsChange request) {
        mapper.update(new SnipBbs(uid, request));
    }

    /**
     * @method      findBbsByUid
     * @author      work
     * @date        2025-04-15
     * @deacription {uid} 값으로 게시판 단건 조회
    **/
    @Transactional(readOnly = true)
    public SnipBbsView findBbsByUid(Long uid) {
        return mapper.getDetail(uid);
    }

    /**
     * @method      findAllBbsPage
     * @author      work
     * @date        2025-04-15
     * @deacription 게시판 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<SnipBbsList> findAllBbsPage(SnipBbsParam param, Pageable pageable) {
        List<SnipBbsList> list = mapper.page(pageable.generateMap(param));
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
    public List<SnipBbsList> findAllBbsList(SnipBbsParam param) {
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
        mapper.delete(new SnipBbs(bbsUid));
    }

}
