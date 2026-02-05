package hong.snipp.link.snipp_link.domain.verifycode.service;

import hong.snipp.link.snipp_link.domain.verifycode.dto.request.SnippCheckVerifyCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.verifycode.service
 * fileName       : SnippVerifyCodeRestController
 * author         : work
 * date           : 2025-04-22
 * description    : 유저에게 발송된 인증코드 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 * 2026-02-05        work       /snipp/api/verify-code => /api/snipp/verify-code
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snipp/verify-code")
public class SnippVerifyCodeRestController {

    private final SnippVerifyCodeService service;

    /**
     *
     * 인증번호 유효성 체크
     *
     * @api         [PUT] /api/snipp/verify-code/check-verify-code
     * @author      work
     * @date        2025-04-22
    **/
    @PutMapping("/check-verify-code")
    public ResponseEntity checkVerifyCode(@RequestBody @Valid SnippCheckVerifyCode request) {
        boolean canUse = service.checkVerifyCode(request);
        return ResponseEntity.ok(canUse);
    }
}
