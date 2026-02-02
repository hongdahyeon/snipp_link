package hong.snipp.link.snipp_link.global.session.springsession;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;


/**
 * packageName    : hong.snipp.link.snipp_link.global.session.springsession
 * fileName       : SpringSessionConfig
 * author         : work
 * date           : 2025-04-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-02        work       최초 생성
 *                              => {matchIfMissing=true}: 해당 속성(spring.session.store-type)이 application.yml에 존재하지 않아도 true로 간주하고 빈생성
 */

@Configuration
@EnableSpringHttpSession
@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "none", matchIfMissing = true)
public class SpringSessionConfig {

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    public SessionRepository<MapSession> sessionRepository() {
        MapSessionRepository mapSessionRepository = new MapSessionRepository(new ConcurrentHashMap<>());
        mapSessionRepository.setDefaultMaxInactiveInterval(Duration.ofHours(1)); // 세션유지 1시간
        return mapSessionRepository;
    }
}
