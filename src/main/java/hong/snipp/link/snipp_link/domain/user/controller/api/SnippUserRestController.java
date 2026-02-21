package hong.snipp.link.snipp_link.domain.user.controller.api;

import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserChange;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserChangePwd;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserSave;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserSearch;
import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserList;
import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.domain.user.service.SnippUserService;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.controller.api
 * fileName       : SnippUserRestController
 * author         : work
 * date           : 2025-04-15
 * description    : 유저 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-21        work       이메일/아이디 중복 체크 API + 저장 API 추가
 * 2025-04-22        work       비번 변경 및 90일 연장 API 추가
 * 2025-04-23        work       {findAllUserPage, findAllUserList} API 추가
 * 2025-04-25        work       유저 단건 조회/수정 + 유저 잠금 및 활성화 수정 API 추가
 * 2026-01-12        home       유저 목록 조회 (페이징) super 권한
 * 2026-01-13        work       유저 잠금/활성화 관련 super 권한
 * 2026-02-05        work       /snipp/api/user => /api/snipp/user
 * 2026-02-22        home       패키지 구조 변경
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/user")
public class SnippUserRestController {

    private final SnippUserService service;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     *
     * 사용자 아이디, 이메일 중복 체크
     * > code : id / email
     * -> value : id(id값) / email(email값)
     *
     * @api         [GET] /api/snipp/user/{code}/duplicate-check
     * @author      work
     * @date        2025-04-21
     **/
    @GetMapping("/{code}/duplicate-check")
    public ResponseEntity duplicateCheck(@PathVariable("code") String code, @RequestParam(name = "value") String value) {
        Map<String, Object> map = new HashMap<>();
        boolean isExists = false;
        String message = "";
        if("id".equals(code)) {

            isExists = service.isExistUserId(value);
            if(isExists) message = "중복되는 사용자ID 입니다.";
            else message = "사용 가능한 사용자ID 입니다.";
            map.put("checkCanUse", !isExists);

        } else if("email".equals(code)) {

            boolean validEmail = isValidEmail(value);
            map.put("invalid", (!validEmail));
            if(!validEmail) {
                message = "유효하지 않은 이메일 형식입니다.";
                map.put("checkCanUse", false);
            } else {
                isExists = service.isExistUserEmail(value);
                if(isExists) message = "중복되는 이메일입니다.";
                else message = "사용 가능한 이메일입니다.";
                map.put("checkCanUse", !isExists);
            }
        }
        map.put("message", message);
        return ResponseEntity.ok(map);
    }

    /**
     *
     * 사용자 저장
     *
     * @api         [POST] /api/snipp/user
     * @author      work
     * @date        2025-04-21
    **/
    @PostMapping
    public ResponseEntity saveUser(@RequestBody @Valid SnippUserSave request) {
        service.saveUser(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 비밀번호 수정 및 90일 연장
     *
     * @api         [PUT] /api/snipp/user/change-password
     * @author      work
     * @date        2025-04-22
    **/
    @PutMapping("/change-password")
    public ResponseEntity changeUserPassword(@RequestBody @Valid SnippUserChangePwd request) {
        service.changeUserPassword(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 휴먼 계정 (로그인 안한지 1년 지남) -> 이메일 인증번호로 풀기
     *
     * @api         [GET] /api/snipp/user/is-expired
     * @author      work
     * @date        2025-04-22
    **/
    @GetMapping("/is-expired")
    public ResponseEntity changeUserExpired(@RequestParam("userEmail") String userEmail) {
        service.changeUserExpired(userEmail);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 유저 목록 조회 (페이징)
     * [권한] ROLE_SUPER
     *
     * @api         [GET] /api/snipp/user/super/page
     * @author      work
     * @date        2025-04-23
    **/
    @GetMapping("/super/page")
    public ResponseEntity findAllUserPage(@Valid SnippUserSearch search, Pageable pageable) {
        Page<SnippUserList> allUserPage = service.findAllUserPage(search, pageable);
        return ResponseEntity.ok(allUserPage);
    }


    /**
     *
     * 유저 목록 조회 (리스트)
     *
     * @api         [GET] /api/snipp/user/list
     * @author      work
     * @date        2025-04-23
    **/
    @GetMapping("/list")
    public ResponseEntity findAllUserList(@Valid SnippUserSearch search) {
        List<SnippUserList> allUserList = service.findAllUserList(search);
        return ResponseEntity.ok(allUserList);
    }

    /**
     *
     * 유저 단건 조회
     *
     * @api         [GET] /api/snipp/user/{uid}
     * @author      work
     * @date        2025-04-25
    **/
    @GetMapping("/{uid}")
    public ResponseEntity findUserByUserUid(@PathVariable("uid") Long uid) {
        SnippUserView userByUserUid = service.findUserByUserUid(uid);
        return ResponseEntity.ok(userByUserUid);
    }

    /**
     *
     * 유저 잠금 풀기/잠그기 + 유저 활성화/비활성화 시키기
     * * code
     * => 잠금    : lock / unlock
     * => 활성화  : enable / disable
     *
     * @api         [PUT] /api/snipp/user/super/{code}/{uid}
     * @author      work
     * @date        2025-04-25
    **/
    @PutMapping("/super/{code}/{uid}")
    public ResponseEntity changeUserInfo(@PathVariable("code") String code, @PathVariable("uid") Long uid) {
        if("lock".equals(code) || "unlock".equals(code)) {

            String value = ("lock".equals(code)) ? "Y" : "N";
            service.changeUserLock(value, uid);

        } else if ("enable".equals(code) || "disable".equals(code)) {

            String value = ("enable".equals(code)) ? "Y" : "N";
            service.changeUserEnable(value, uid);

        }
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 유저 단건 수정
     *
     * @api         [PUT] /api/snipp/user/{uid}
     * @author      work
     * @date        2025-04-25
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeUser(@PathVariable("uid") Long uid, @RequestBody @Valid SnippUserChange request) {
        service.changeUser(uid, request);
        return ResponseEntity.ok().build();
    }
}
