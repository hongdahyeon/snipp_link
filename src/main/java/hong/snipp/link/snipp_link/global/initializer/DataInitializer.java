package hong.snipp.link.snipp_link.global.initializer;

import hong.snipp.link.snipp_link.domain.bbs.dto.request.SnippBbsSave;
import hong.snipp.link.snipp_link.domain.bbs.service.SnippBbsService;
import hong.snipp.link.snipp_link.domain.code.BbsTp;
import hong.snipp.link.snipp_link.domain.code.UserRole;
import hong.snipp.link.snipp_link.domain.user.dto.request.SnippUserInitSave;
import hong.snipp.link.snipp_link.domain.user.service.SnippUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * packageName    : hong.snipp.link.snipp_link.global.initializer
 * fileName       : DataInitializer
 * author         : work
 * date           : 2026-02-09
 * description    : 애플리케이션 실행 시점에 초기 데이터 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-09        work       최초 생성
 */

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final SnippUserService userService;
    private final SnippBbsService bbsService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            for(UserRole role : UserRole.values()) {
                boolean ifExist = userService.isExistUserRole(role.name());
                if(!ifExist) {
                    String initData = role.getInitData();
                    String userEmail = initData + "@snipp.com";
                    String password = initData + "1234!";
                    userService.saveInitUser(new SnippUserInitSave(initData, userEmail, password, initData, role.name()));
                }
            }
            for(BbsTp bbsTp : BbsTp.values()) {
                boolean ifExist = bbsService.isExistBbsTp(bbsTp.name());
                if(!ifExist) {
                    bbsService.saveBbs(new SnippBbsSave(bbsTp.name(), bbsTp.getDescription(), "Y"));
                }
            }
        } catch (Exception e) {
            log.error(">>>> 데이터 초기 세팅 중 시스템 오류 발생: {}", e.getMessage());
        }
    }
}
