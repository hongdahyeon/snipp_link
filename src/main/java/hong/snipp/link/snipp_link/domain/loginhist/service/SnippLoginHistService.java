package hong.snipp.link.snipp_link.domain.loginhist.service;

import hong.snipp.link.snipp_link.domain.loginhist.domain.SnippLoginHist;
import hong.snipp.link.snipp_link.domain.loginhist.domain.SnippLoginHistMapper;
import hong.snipp.link.snipp_link.domain.loginhist.dto.request.SnippLoginHistSave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
