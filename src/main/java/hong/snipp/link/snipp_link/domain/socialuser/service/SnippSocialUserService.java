package hong.snipp.link.snipp_link.domain.socialuser.service;

import hong.snipp.link.snipp_link.domain.socialuser.entity.SnippSocialUser;
import hong.snipp.link.snipp_link.domain.socialuser.dao.SnippSocialUserMapper;
import hong.snipp.link.snipp_link.domain.socialuser.dto.request.SnippOAuth2UserSave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.socialuser.service
 * fileName       : SnippSocialUserService
 * author         : work
 * date           : 2025-04-15
 * description    : 소셜 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-21        work       {saveSocialUser} 메소드 추가
 */
@Service
@RequiredArgsConstructor
public class SnippSocialUserService {

    private final SnippSocialUserMapper mapper;

    /**
     * @method      saveSocialUser
     * @author      work
     * @date        2025-04-21
     * @deacription 소셜 사용자 저장
    **/
    @Transactional
    public Long saveSocialUser(SnippOAuth2UserSave request) {
        SnippSocialUser bean = new SnippSocialUser(request);
        mapper.insert(bean);
        return bean.getUid();
    }
}
