package hong.snipp.link.snipp_link.domain.loginhist.service;

import hong.snipp.link.snipp_link.domain.loginhist.domain.SnippLoginHist;
import hong.snipp.link.snipp_link.domain.loginhist.domain.SnippLoginHistMapper;
import hong.snipp.link.snipp_link.domain.loginhist.dto.request.SnippLoginHistParam;
import hong.snipp.link.snipp_link.domain.loginhist.dto.request.SnippLoginHistSave;
import hong.snipp.link.snipp_link.domain.loginhist.dto.response.SnippLoginHistList;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.loginhist.service
 * fileName       : SnippLoginHistService
 * author         : work
 * date           : 2025-04-21
 * description    : 로그인 이력 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-21        work       최초 생성
 * 2025-04-28        work       {findAllLoginHistPage, findAllLoginHistList} 메소드 추가
 */
@Service
@RequiredArgsConstructor
public class SnippLoginHistService {

    private final SnippLoginHistMapper mapper;

    /**
     * @method      saveLoginHist
     * @author      work
     * @date        2025-04-21
     * @deacription 로그인 이력 저장
    **/
    @Transactional
    public void saveLoginHist(SnippLoginHistSave request) {
        mapper.insert(new SnippLoginHist(request));
    }

    /**
     * @method      findAllLoginHistPage
     * @author      work
     * @date        2025-04-28
     * @deacription 로그인 이력 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<SnippLoginHistList> findAllLoginHistPage(SnippLoginHistParam param, Pageable pageable) {
        List<SnippLoginHistList> page = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(page, count, pageable);
    }

    /**
     * @method      findAllLoginHistList
     * @author      work
     * @date        2025-04-28
     * @deacription 로그인 이력 목록 조회 (리스트)
     **/
    @Transactional(readOnly = true)
    public List<SnippLoginHistList> findAllLoginHistList(SnippLoginHistParam param) {
        return mapper.list(param);
    }
}
