package hong.snipp.link.snipp_link.domain.verifycode.service;

import hong.snipp.link.snipp_link.domain.verifycode.entity.SnippVerifyCode;
import hong.snipp.link.snipp_link.domain.verifycode.dao.SnippVerifyCodeMapper;
import hong.snipp.link.snipp_link.domain.verifycode.dto.request.SnippCheckVerifyCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.verifycode.service
 * fileName       : SnippVerifyCodeService
 * author         : work
 * date           : 2025-04-22
 * description    : 유저에게 발송된 인증 코드 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
 */
@Service
@RequiredArgsConstructor
public class SnippVerifyCodeService {

    private final SnippVerifyCodeMapper mapper;

    /**
     * @method      saveVerifyCode
     * @author      work
     * @date        2025-04-22
     * @deacription 이메일로 발송한 인증번호 저장
    **/
    @Transactional
    public void saveVerifyCode(String userEmail, String verifyCode) {
        mapper.insert(new SnippVerifyCode(userEmail, verifyCode));
    }

    /**
     * @method      checkVerifyCode
     * @author      work
     * @date        2025-04-22
     * @deacription {userEmail, verifyCode} 값으로 인증 코드 유효성 체크 -> 해당 코드 expire 시키기
    **/
    @Transactional
    public boolean checkVerifyCode(SnippCheckVerifyCode request) {
        Map<String, String> map = Map.of(
          "userEmail", request.getUserEmail(),
          "verifyCode", request.getVerifyCode()
        );
        int count = mapper.countVerifyCode(map);
        if(count == 1) {

            mapper.updateVerifyCodeExpired(map);
            mapper.updateUserLastLoginDt(request.getUserEmail());
            return true;

        } else return false;
    }

}
