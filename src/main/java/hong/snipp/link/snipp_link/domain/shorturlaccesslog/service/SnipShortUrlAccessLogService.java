package hong.snipp.link.snipp_link.domain.shorturlaccesslog.service;

import hong.snipp.link.snipp_link.domain.shorturlaccesslog.domain.SnipShortUrlAccessLog;
import hong.snipp.link.snipp_link.domain.shorturlaccesslog.domain.SnipShortUrlAccessLogMapper;
import hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.request.SnipShortUrlAccessLogParam;
import hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.request.SnipShortUrlAccessLogSave;
import hong.snipp.link.snipp_link.domain.shorturlaccesslog.dto.response.SnipShortUrlAccessLogList;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccesslog.service
 * fileName       : SnipShortUrlAccessLogService
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class SnipShortUrlAccessLogService {

    private final SnipShortUrlAccessLogMapper mapper;

    /**
     * @method      saveShortURLAccessLog
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 접근 로그 저장
    **/
    @Transactional
    public void saveShortURLAccessLog(SnipShortUrlAccessLogSave request) {
        mapper.insert(new SnipShortUrlAccessLog(request));
    }

    /**
     * @method      findAllLogPage
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 접근 로그 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<SnipShortUrlAccessLogList> findAllLogPage(SnipShortUrlAccessLogParam param, Pageable pageable) {
        List<SnipShortUrlAccessLogList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllLogList
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 접근 로그 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<SnipShortUrlAccessLogList> findAllLogList(SnipShortUrlAccessLogParam param) {
        return mapper.list(param);
    }
}
