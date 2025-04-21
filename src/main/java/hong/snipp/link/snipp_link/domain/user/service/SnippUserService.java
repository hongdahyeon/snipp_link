package hong.snipp.link.snipp_link.domain.user.service;

import hong.snipp.link.snipp_link.domain.code.UserRole;
import hong.snipp.link.snipp_link.domain.user.domain.SnippUser;
import hong.snipp.link.snipp_link.domain.user.domain.SnippUserMapper;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserSave;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.service
 * fileName       : SnippUserService
 * author         : work
 * date           : 2025-04-15
 * description    : 유저 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-21        work       {isExistUserId, isExistUserEmail, saveUser} 메소드 추가
 */
@Service
@RequiredArgsConstructor
public class SnippUserService {

    private final PasswordEncoder passwordEncoder;
    private final SnippUserMapper mapper;

    /**
     * @method      isExistUserId
     * @author      work
     * @date        2025-04-21
     * @deacription {userId} 값을 갖고 있는 유저가 있는지 체크
     *              -> 있다면 : true  (중복되는 userId)
     *              -> 없다면 : false (사용 가능한 userId)
    **/
    @Transactional(readOnly = true)
    public boolean isExistUserId(String userId) {
        int findUserIdCnt = mapper.countByUserId(userId);
        return findUserIdCnt == 1;
    }

    /**
     * @method      isExistUserEmail
     * @author      work
     * @date        2025-04-21
     * @deacription {userEmail} 값을 갖고 있는 유저가 있는지 체크
     *              -> 있다면 : true (중복되는 userEmail)
     *              -> 없다면 : false (사용 가능한 userEmail)
    **/
    @Transactional(readOnly = true)
    public boolean isExistUserEmail(String userEmail) {
        int findUserEmailCnt = mapper.countByUserEmail(userEmail);
        return findUserEmailCnt == 1;
    }

    /**
     * @method      saveUser
     * @author      work
     * @date        2025-04-21
     * @deacription 폼 로그인 유저 저장
    **/
    @Transactional
    public void saveUser(SnippUserSave request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        mapper.insert(new SnippUser(request, encodePassword));
    }
}
