package hong.snipp.link.snipp_link.domain.user.service;

import hong.snipp.link.snipp_link.domain.socialuser.dto.request.SnippOAuth2UserSave;
import hong.snipp.link.snipp_link.domain.socialuser.service.SnippSocialUserService;
import hong.snipp.link.snipp_link.domain.user.domain.SnippUser;
import hong.snipp.link.snipp_link.domain.user.domain.SnippUserMapper;
import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.service
 * fileName       : SnippAuthUserService
 * author         : work
 * date           : 2025-04-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 * 2025-04-21        work       {findUserByUserId, findUserByUserEmail} 메소드 추가
 */

@Service
@RequiredArgsConstructor
public class SnippAuthUserService {

    private final SnippUserMapper mapper;
    private final SnippSocialUserService socialUserService;

    /**
     * @method      findUserByUserId
     * @author      work
     * @date        2025-04-21
     * @deacription {userId} 값으로 사용자 찾기
    **/
    @Transactional(readOnly = true)
    public SnippUserView findUserByUserId(String userId) {
        return mapper.getUserByUserId(userId);
    }

    /**
     * @method      countByUserEmail
     * @author      work
     * @date        2025-04-21
     * @deacription {userEmail} 값으로 사용자 명수 카운팅
    **/
    @Transactional(readOnly = true)
    public int countByUserEmail(String userEmail) {
        return mapper.countByUserEmail(userEmail);
    }

    /**
     * @method      saveOAuth2User
     * @author      work
     * @date        2025-04-21
     * @deacription 유저 저장 (소셜 사용자)
    **/
    @Transactional
    public SnippUserView saveOAuth2User(SnippOAuth2UserSave request) {
        Long socialUid = socialUserService.saveSocialUser(request);
        String encodePassword = new BCryptPasswordEncoder().encode(request.getUserEmail());
        mapper.insert(new SnippUser(request, encodePassword, socialUid));
        return this.findUserByUserId(request.getUserId());
    }

    /**
     * @method      resetLastLoginDtAndPwdFailCntByUserId
     * @author      work
     * @date        2025-04-21
     * @deacription 가장 최근 로그인 일자 & 비밀번호 틀린 횟수 초기화 (by. userId)
    **/
    @Transactional
    public void resetLastLoginDtAndPwdFailCntByUserId(String userId) {
        mapper.updateLastLoginDtAndPwdFailCntByUserId(userId);
    }

    /**
     * @method      resetLastLoginDtAndPwdFailCntByUserEmail
     * @author      work
     * @date        2025-04-21
     * @deacription 가장 최근 로그인 일자 & 비밀번호 틀린 횟수 초기화 (by. userEmail)
    **/
    @Transactional
    public void resetLastLoginDtAndPwdFailCntByUserEmail(String userEmail) {
        mapper.updateLastLoginDtAndPwdFailCntByUserEmail(userEmail);
    }

    /**
     * @method      changePwdFailCnt
     * @author      work
     * @date        2025-04-21
     * @deacription 로그인 시점, 비밀번호 오류 : 비밀번호 실패 횟수 업데이트
     *              => 5회 실패 시점에는 사용자 락 걸기
    **/
    @Transactional
    public void changePwdFailCnt(String userId, int pwdFailCnt) {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("userId", userId);
            put("pwdFailCnt", pwdFailCnt);
        }};
        mapper.updatePwdFailCnt(params);
    }
}
