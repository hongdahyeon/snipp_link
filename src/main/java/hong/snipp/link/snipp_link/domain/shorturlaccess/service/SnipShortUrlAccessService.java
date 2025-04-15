package hong.snipp.link.snipp_link.domain.shorturlaccess.service;

import hong.snipp.link.snipp_link.domain.shorturlaccess.domain.SnipShortUrlAccess;
import hong.snipp.link.snipp_link.domain.shorturlaccess.domain.SnipShortUrlAccessMapper;
import hong.snipp.link.snipp_link.domain.shorturlaccess.dto.response.SnipShortUrlAccessList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccess.service
 * fileName       : SnipShortUrlAccessService
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 권한 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class SnipShortUrlAccessService {

    private final SnipShortUrlAccessMapper mapper;

    /**
     * @method      saveAccessUsers
     * @author      work
     * @date        2025-04-15
     * @deacription 접근 가능 사용자 저장
    **/
    @Transactional
    public void saveAccessUsers(Long shortenUrlUid, List<Long> users) {
        for(Long userUid : users) {
            mapper.insert(new SnipShortUrlAccess(shortenUrlUid, userUid));
        }
    }

    /**
     * @method      ifUserCanAccess
     * @author      work
     * @date        2025-04-15
     * @deacription {userUid} 유저가 {shortURL} 접근 가능한지 체크
    **/
    @Transactional(readOnly = true)
    public boolean ifUserCanAccess(Long userUid, String shortURL) {
        Map<String, Object> map = Map.of(
                "userUid", userUid,
                "shortUrl", shortURL
        );
        int count = mapper.countByUserAndShortURL(map);
        return count == 1;
    }

    /**
     * @method      findAccessListByUid
     * @author      work
     * @date        2025-04-15
     * @deacription {shortenUrlUid}에 접근 가능한 사용자 정보 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<SnipShortUrlAccessList> findAccessListByUid(Long shortenUrlUid) {
        return mapper.getAccessListByUid(shortenUrlUid);
    }
}
