package hong.snipp.link.snipp_link.domain.user.domain;

import hong.snipp.link.snipp_link.domain.code.UserRole;
import hong.snipp.link.snipp_link.domain.socialuser.dto.request.SnippOAuth2UserSave;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserChange;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserChangePwd;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserInitSave;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.domain
 * fileName       : SnippUser
 * author         : work
 * date           : 2025-04-15
 * description    : 유저 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       필드 추가
 *                              : userNm , userRole ,  lastConnDt , lastPwdChngDt , pwdFailCnt , isLocked , isEnable
 * 2025-04-21        work       소셜/폼 로그인 유저 저장용 생성자 추가
 * 2025-04-22        work       유저의 비밀번호 변경 및 비번 변경일 연장 생성자 추가
 * 2025-04-25        work       유저 수정 관련 생성자 추가
 * 2026-02-09        work       초기 데이터 추가를 위한 생성자 추가
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippUser {

    private Long uid;
    private String userId;
    private String password;
    private String userEmail;
    private String userNm;
    private Long socialUid;
    private String userRole;
    private String lastConnDt;
    private String lastPwdChngDt;
    private int pwdFailCnt;
    private String isLocked;
    private String isEnable;

    /**
     * @method      SnippUser 생성자 1
     * @author      work
     * @date        2025-04-21
     * @deacription 소셜 로그인 유저 저장용 생성자
    **/
    public SnippUser(SnippOAuth2UserSave request, String encodePassword, Long socialUid) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userEmail = request.getUserEmail();
        this.userNm = request.getUserEmail();
        this.userRole = UserRole.ROLE_USER.name();
        this.socialUid = socialUid;
    }

    /**
     * @method      SnippUser 생성자 2-1
     * @author      work
     * @date        2025-04-21
     * @deacription 폼 로그인 유저 저장용 생성자
    **/
    public SnippUser(SnippUserSave request, String encodePassword) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userEmail = request.getUserEmail();
        this.userNm = request.getUserNm();
        this.userRole = UserRole.ROLE_USER.name();
    }

    /**
     * @method      SnippUser 생성자 2-2
     * @author      dahyeon
     * @date        2026-02-09
     * @deacription 초기 유저 데이터 생성용 생성자
    **/
    public SnippUser(SnippUserInitSave request, String encodePassword) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userEmail = request.getUserEmail();
        this.userNm = request.getUserNm();
        this.userRole = request.getRole();
    }

    /**
     * @method      SnippUser 생성자 3
     * @author      work
     * @date        2025-04-22
     * @deacription 유저 비밀번호 변경
    **/
    public SnippUser(SnippUserChangePwd request, String encodePassword) {
        this.userId = request.getUserId();
        this.password = encodePassword;
    }

    /**
     * @method      SnippUser 생성자 4
     * @author      work
     * @date        2025-04-22
     * @deacription 유저 비밀번호 변경일 90일 연장
    **/
    public SnippUser(SnippUserChangePwd request) {
        this.userId = request.getUserId();
    }

    /**
     * @method      SnippUser 생성자 5
     * @author      work
     * @date        2025-04-25
     * @deacription => (isLock = true)  : 유저 잠금 / 잠금 풀기
     *              => (isLock = false) : 유저 활성화 / 잠금 비활성화
    **/
    public SnippUser(boolean isLock, String value, Long uid) {
        if(isLock) this.isLocked = value;
        else this.isEnable = value;
        this.uid = uid;
    }

    /**
     * @method      SnippUser 생성자 6
     * @author      work
     * @date        2025-04-25
     * @deacription 유저 단건 수정
    **/
    public SnippUser(Long uid, SnippUserChange reqeust, String encodePassword) {
        this.uid = uid;
        this.userNm = reqeust.getUserNm();
        this.userEmail = reqeust.getUserEmail();
        this.password = encodePassword;
    }
}
