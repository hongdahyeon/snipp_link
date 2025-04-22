package hong.snipp.link.snipp_link.domain.user.service;

import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserChangePwd;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserSave;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.service
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
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/user")
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
     * @api         [GET] /snipp/api/user/{code}/duplicate-check
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
     * @api         [POST] /snipp/api/user
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
     * @api         [PUT] /snipp/api/user/change-password
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
     * @api         [GET] /snipp/user/is-expired
     * @author      work
     * @date        2025-04-22
    **/
    @GetMapping("/is-expired")
    public ResponseEntity changeUserExpired(@RequestParam("userEmail") String userEmail) {
        service.changeUserExpired(userEmail);
        return ResponseEntity.ok().build();
    }
}
