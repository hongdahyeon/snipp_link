package hong.snipp.link.snipp_link.domain.user.service;

import hong.snipp.link.snipp_link.domain.enumcode.SocialTp;
import hong.snipp.link.snipp_link.domain.enumcode.UserRole;
import hong.snipp.link.snipp_link.domain.user.entity.SnippUser;
import hong.snipp.link.snipp_link.domain.user.dao.SnippUserMapper;
import hong.snipp.link.snipp_link.domain.user.dto.request.*;
import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserList;
import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.domain.verifycode.service.SnippVerifyCodeService;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import hong.snipp.link.snipp_link.global.hong.google.GoogleEmailService;
import hong.snipp.link.snipp_link.global.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
 * 2025-04-22        work       {changeUserPassword, changeUserExpired} 메소드 추가
 * 2025-04-23        work       {findAllUserPage, findAllUserList} 메소드 추가
 * 2025-04-25        work       {findUserByUserUid, changeUserLock, changeUserEnable, changeUser} 메소드 추가
 * 2026-01-12        home       findAllUserPage 소셜로그인 사용자 한국어 kor
 * 2026-02-05        work       findUserByUserId 추가
 * 2026-02-09        work       isExistUserRole 추가
 */
@Service
@RequiredArgsConstructor
public class SnippUserService {

    private final PasswordEncoder passwordEncoder;
    private final SnippUserMapper mapper;
    private final GoogleEmailService emailService;
    private final SnippVerifyCodeService verifyCodeService;

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
     * @method      isExistUserRole
     * @author      dahyeon
     * @date        2026-02-09
     * @deacription {role} 값을 갖고 있는 유저가 있는지 체크
     *              -> 있다면: true (초기 데이터 role 갖는 유저 추가)
     *              -> 없다면: false
    **/
    @Transactional(readOnly = true)
    public boolean isExistUserRole(String role) {
        int findUserRoleCnt = mapper.countByUserRole(role);
        return findUserRoleCnt != 0;
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

    @Transactional
    public void saveInitUser(SnippUserInitSave request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        mapper.insert(new SnippUser(request, encodePassword));
    }

    /**
     * @method      changeUserPassword
     * @author      work
     * @date        2025-04-22
     * @deacription 유저 비밀번호 변경 or 90일 연장
    **/
    @Transactional
    public void changeUserPassword(SnippUserChangePwd request) {
        if(request.isChange()) {
            String encodePassword = passwordEncoder.encode(request.getPassword());
            mapper.updateUserPassword(new SnippUser(request, encodePassword));
        } else {
            mapper.updateUserChangePasswordDt(new SnippUser(request));
        }
    }

    /**
     * @method      changeUserExpired
     * @author      work
     * @date        2025-04-22
     * @deacription 휴먼 계정이 된 사용자에게 이메일 발송
    **/
    @Transactional
    public void changeUserExpired(String userEmail) {
        String verificationCode = StringUtil.random(8);
        verifyCodeService.saveVerifyCode(userEmail, verificationCode);

        String subject = "[SnippLink] 인증번호 발송";
        String content = "<html>"
                + "<body>"
                + "<div style='background-color: #f8f9fa; padding: 30px; border-radius: 8px; max-width: 600px; margin: 0 auto;'>"
                + "<div style='text-align: center; color: #0d6efd;'>"
                + "<h2>[SnippLink] 인증번호 발송</h2>"
                + "</div>"
                + "<div style='margin-top: 20px; font-size: 16px;'>"
                + "<p>안녕하세요!</p>"
                + "<p>귀하의 인증번호는 아래와 같습니다:</p>"
                + "<div style='display: inline-block; font-size: 24px; font-weight: bold; color: #0d6efd; padding: 8px 16px; background-color: #e9ecef; border-radius: 4px;'>"
                + verificationCode
                + "</div>"
                + "<p>위 인증번호를 입력해 주세요.</p>"
                + "</div>"
                + "<div style='margin-top: 20px; text-align: center; font-size: 14px; color: #6c757d;'>"
                + "<p>감사합니다.</p>"
                + "<p>SnippLink</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        emailService.sendEmail(userEmail, subject, content);
    }

    /**
     * @method      findAllUserPage
     * @author      work
     * @date        2025-04-23
     * @deacription 유저 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<SnippUserList> findAllUserPage(SnippUserSearch search, Pageable pageable) {
        List<SnippUserList> list = mapper.page(pageable.generateMap(search));
        list.forEach((data) -> {
            data.setUserRoleKr(UserRole.getNameByCode(data.getUserRole()));
            if(data.getSocialUid() != null) {
                data.setSocialTpKor(SocialTp.getNameByCode(data.getSocialTp()));
            }
        });
        int count = mapper.count(search);
        return new Page<>(list, count, pageable);
    }


    /**
     * @method      findAllUserList
     * @author      work
     * @date        2025-04-23
     * @deacription 유저 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<SnippUserList> findAllUserList(SnippUserSearch search) {
        return mapper.list(search);
    }


    /**
     * @method      findUserByUserUid
     * @author      work
     * @date        2025-04-25
     * @deacription 유저 단건 조회 by.userUid
    **/
    @Transactional(readOnly = true)
    public SnippUserView findUserByUserUid(Long uid) {
        return mapper.getDetail(uid);
    }

    /**
     * @method      findUserByUserId
     * @author      dahyeon
     * @date        2026-02-04
     * @deacription 유저 단건 조회 by.userId
    **/
    @Transactional(readOnly = true)
    public SnippUserView findUserByUserId(String userId) {
        return mapper.getUserByUserId(userId);
    }

    /**
     * @method      changeUserLock
     * @author      work
     * @date        2025-04-25
     * @deacription 유저 잠금 / 잠금 풀기
    **/
    @Transactional
    public void changeUserLock(String isLocked, Long uid) {
        mapper.updateUserLock(new SnippUser(true, isLocked, uid));
    }

    /**
     * @method      changeUserEnable
     * @author      work
     * @date        2025-04-25
     * @deacription 유저 활성화 / 비활성화
    **/
    @Transactional
    public void changeUserEnable(String isEnable, Long uid) {
        mapper.updateUserEnable(new SnippUser(false, isEnable, uid));
    }

    /**
     * @method      changeUser
     * @author      work
     * @date        2025-04-25
     * @deacription 유저 단건 수정
    **/
    @Transactional
    public void changeUser(Long uid, SnippUserChange request) {
        SnippUser view = mapper.view(uid);
        String encodePassword = (request.getPassword() != null) ? passwordEncoder.encode(request.getPassword()) : view.getPassword();
        mapper.update(new SnippUser(uid, request, encodePassword));
    }

}
