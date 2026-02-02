package hong.snipp.link.snipp_link.global.session.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import java.time.Duration;
import java.util.List;


/**
 * packageName    : hong.snipp.link.snipp_link.global.session.redis
 * fileName       : RedisConfig
 * author         : work
 * date           : 2025-04-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-02        work       최초 생성
 */

@Configuration
@RequiredArgsConstructor
/* @EnableRedisHttpSession: Redis를 Spring Session의 저장소로 활성화
 * => redisNamespace: Redis 내에서 세션 키가 섞이지 않도록 구분자(prefix)를 지정
 */
@EnableRedisHttpSession(redisNamespace = "${spring.session.redis.namespace}")
/* @ConditionalOnProperty: 설정 파일(yml)의 store-type이 'redis'일 때만 이 설정 클래스를 빈으로 등록
 * => 이를 통해 로컬(None)과 운영(Redis) 환경을 유연하게 스위칭
 */
@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "redis")
public class RedisConfig {

    private final static Duration sessionTimeoutSeconds = Duration.ofHours(1);

    @Value("${spring.redis.cluster.nodes}")
    private List<String> clusterNodes;

    /**
     * @method      overriddenSessionRepository
     * @author      dahyeon
     * @date        2026-02-02
     * @deacription 기본 세션 레포지토리를 커스텀 설정으로 덮기
    **/
    @Primary
    @Bean
    public RedisIndexedSessionRepository overriddenSessionRepository(RedisIndexedSessionRepository sessionRepository) {
        sessionRepository.setDefaultMaxInactiveInterval(sessionTimeoutSeconds);
        return sessionRepository;
    }

    /**
     * @method      springSessionBackedSessionRegistry
     * @author      dahyeon
     * @date        2026-02-02
     * @deacription Redis를 기반으로 하는 세션 장부(Registry)를생성
     *              => 분산 환경에서 특정 사용자의 세션 리스트 조회 및 강제 만료(expireNow)시 사용
    **/
    @Bean
    public SessionRegistry springSessionBackedSessionRegistry(RedisIndexedSessionRepository repository) {
        return new SpringSessionBackedSessionRegistry<>(repository);
    }

    /**
     * @method      lettuceConnectionFactory
     * @author      dahyeon
     * @date        2026-02-02
     * @deacription 인프라 환경(Standalone vs cluster)에 따라 적절한 Redis 연결 팩토리 생성
     *              => Lettuce 라이브러리를 통해 비동기/논블로킹 방식으로 성능 최적화
    **/
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisConfiguration redisConfiguration;
        if(clusterNodes.size() == 1){
            String[] split = clusterNodes.get(0).split(":");
            redisConfiguration = new RedisStandaloneConfiguration(split[0], Integer.parseInt(split[1]));
        } else {
            redisConfiguration = new RedisClusterConfiguration(clusterNodes);
        }
        return new LettuceConnectionFactory(redisConfiguration);
    }

    /**
     * @method      redisTemplate
     * @author      dahyeon
     * @date        2026-02-02
     * @deacription Redis 데이터 조작을 위한 템플릿 설정
    **/
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());

        // 데이터 정합성을 위해 Spring 트랜잭션(@Transactional)와 연동 설정
        redisTemplate.setEnableTransactionSupport(true);

        // Key는 문자열, Value는 사람이 읽을수 있는 JSON 포맷으로 직렬화
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(new ObjectMapper()));

        return redisTemplate;
    }
}
