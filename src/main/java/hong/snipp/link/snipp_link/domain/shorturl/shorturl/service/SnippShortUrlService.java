package hong.snipp.link.snipp_link.domain.shorturl.shorturl.service;

import hong.snipp.link.snipp_link.domain.shorturl.log.service.SnippSUrlLogService;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.domain.SnippShortUrl;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.domain.SnippShortUrlMapper;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.request.SnippShortUrlCreate;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.request.SnippShortUrlParam;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response.SnippShortUrlList;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response.SnippShortUrlView;
import hong.snipp.link.snipp_link.domain.shorturl.access.service.SnippSUrlAccessService;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import hong.snipp.link.snipp_link.global.exception.SnippException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.shorturl.service
 * fileName       : SnippShortUrlService
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       파일 이름 변경 : snip -> snipp
 * 2026-01-14        work       findAllShortURLPage 로그목록 조회 추가
 */
@Service
@RequiredArgsConstructor
public class SnippShortUrlService {

    private final SnippShortUrlMapper mapper;
    private final SnippSUrlAccessService accessService;
    private final SnippSUrlLogService logService;

    /**
     * @method      findShortURLByOriginURL
     * @author      work
     * @date        2025-04-15
     * @deacription {originURL} 값으로 정보 조회
    **/
    @Transactional(readOnly = true)
    public SnippShortUrlView findShortURLByOriginURL(String originURL) {
        return mapper.getShortURLByOriginURL(originURL);
    }

    /**
     * @method      findShortURLByShortURL
     * @author      work
     * @date        2025-04-15
     * @deacription {shortURL} 값으로 정보 조회
    **/
    @Transactional(readOnly = true)
    public SnippShortUrlView findShortURLByShortURL(String shortURL) {
        return mapper.getShortURLByShortURL(shortURL);
    }

    /**
     * @method      saveOriginURL
     * @author      work
     * @date        2025-04-15
     * @deacription URL 정보 저장
    **/
    @Transactional
    public Long saveOriginURL(SnippShortUrlCreate request) {
        if("N".equals(request.getIsPublic()) && request.getUsers().isEmpty()) {
            throw new SnippException("접근 가능 사용자 목록이 비어있습니다.", HttpStatus.BAD_REQUEST);
        }
        SnippShortUrl bean = new SnippShortUrl(request);
        mapper.insert(bean);
        Long shortenUrlUid = bean.getUid();
        if("N".equals(request.getIsPublic())) {
            List<Long> users = request.getUsers();
            accessService.saveAccessUsers(shortenUrlUid, users);
        }
        return shortenUrlUid;
    }

    /**
     * @method      ifShortURLExist
     * @author      work
     * @date        2025-04-15
     * @deacription {shortURL} 있는지 체크
     *              => 있으면 true
     *              => 없으면 false
    **/
    @Transactional(readOnly = true)
    public boolean ifShortURLExist(String shortURL) {
        int count = mapper.countByShortURL(shortURL);
        return count != 0;
    }

    /**
     * @method      changeShortURL
     * @author      work
     * @date        2025-04-15
     * @deacription {uid}에 해당하는 {shortURL} 업데이트
    **/
    @Transactional
    public void changeShortURL(Long uid, String shortURL) {
        mapper.update(new SnippShortUrl(uid, shortURL));
    }


    /**
     * @method      findAllShortURLPage
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 목록 조회 (페이징)
     **/
    @Transactional(readOnly = true)
    public Page<SnippShortUrlList> findAllShortURLPage(SnippShortUrlParam param, Pageable pageable) {
        List<SnippShortUrlList> list = mapper.page(pageable.generateMap(param));
        list.forEach(dto -> {
           if("N".equals(dto.getIsPublic())) {
               dto.setAccessLists(accessService.findAccessListByUid(dto.getUid()));
           }
           dto.setLogLists(logService.findAllLogListByShortLink(dto.getShortUrl()));
        });
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }


    /**
     * @method      findAllShortURLList
     * @author      work
     * @date        2025-04-15
     * @deacription SHORT_URL 목록 조회 (리스트)
     **/
    @Transactional(readOnly = true)
    public List<SnippShortUrlList> findAllShortURLList(SnippShortUrlParam param) {
        List<SnippShortUrlList> list = mapper.list(param);
        list.forEach(dto -> {
            if("N".equals(dto.getIsPublic())) {
                dto.setAccessLists(accessService.findAccessListByUid(dto.getUid()));
            }
        });
        return list;
    }
}
