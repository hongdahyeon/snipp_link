package hong.snipp.link.snipp_link.domain.shorturl.log.service;

import hong.snipp.link.snipp_link.domain.shorturl.log.entity.SnippSUrlLog;
import hong.snipp.link.snipp_link.domain.shorturl.log.dao.SnippSUrlLogMapper;
import hong.snipp.link.snipp_link.domain.shorturl.log.dto.request.SnippSUrlLogParam;
import hong.snipp.link.snipp_link.domain.shorturl.log.dto.request.SnippSUrlLogSave;
import hong.snipp.link.snipp_link.domain.shorturl.log.dto.response.SnippSUrlLogList;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.log.service
 * fileName       : SnippSUrlLogService
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccessLog -> SUrlLog
 * 2026-01-14        work       findAllLogListByShortLink 추가
 */

@Service
@RequiredArgsConstructor
public class SnippSUrlLogService {

    private final SnippSUrlLogMapper mapper;

    /**
     * @method      saveShortURLAccessLog
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 접근 로그 저장
    **/
    @Transactional
    public void saveShortURLAccessLog(SnippSUrlLogSave request) {
        mapper.insert(new SnippSUrlLog(request));
    }

    /**
     * @method      findAllLogPage
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 접근 로그 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<SnippSUrlLogList> findAllLogPage(SnippSUrlLogParam param, Pageable pageable) {
        List<SnippSUrlLogList> list = mapper.page(pageable.generateMap(param));
        list.forEach(dto -> dto.setAccessTp(dto.getAccessTp()));
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
    public List<SnippSUrlLogList> findAllLogList(SnippSUrlLogParam param) {
        List<SnippSUrlLogList> list = mapper.list(param);
        list.forEach(dto -> dto.setAccessTp(dto.getAccessTp()));
        return list;
    }

    /**
     * @method      findAllLogListByShortLink
     * @author      dahyeon
     * @date        2026-01-14
     * @deacription SHORT_URL 접근 로그 목록 조회 (리스트) [BY) SHORT_URL]
    **/
    @Transactional(readOnly = true)
    public List<SnippSUrlLogList> findAllLogListByShortLink(String shortUrl) {
        List<SnippSUrlLogList> list = mapper.getAllLogListByShortLink(shortUrl);
        list.forEach(dto -> dto.setAccessTp(dto.getAccessTp()));
        return list;
    }
}
