package hong.snipp.link.snipp_link.global.session.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
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
@Slf4j
public class RedisConfig {

    @Value("${spring.session.timeout}") // yml의 1h 값을 가져옴
    private Duration springSessionTimeout;

    private final RedisProperties redisProperties;

    /**
     * @method      overriddenSessionRepository
     * @author      dahyeon
     * @date        2026-02-02
     * @deacription 직접 정의한 redisTemplate을 주입받아 세션 레포지토리 등록
    **/
    @Primary
    @Bean
    public RedisIndexedSessionRepository overriddenSessionRepository(@Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate) { // 내가 만든 템플릿 주입
        RedisIndexedSessionRepository repository = new RedisIndexedSessionRepository(redisTemplate);
        repository.setDefaultMaxInactiveInterval(springSessionTimeout);
        return repository;
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
        /*
        * Creates a new RedisClusterConfiguration for given hostPort combinations.
        *   - clusterHostAndPorts[0] = 127.0.0.1:23679
        *   - clusterHostAndPorts[1] = 127.0.0.1:23680 ...
        *  -> Params: clusterNodes – must not be null.
        * */
        if (redisProperties.getCluster() != null && redisProperties.getCluster().getNodes() != null) {
            log.info(">>>> [운영] Redis 모드: CLUSTER (Nodes: {})", redisProperties.getCluster().getNodes());
            return new LettuceConnectionFactory(new RedisClusterConfiguration(redisProperties.getCluster().getNodes()));
        }
        log.info(">>>> [로컬] Redis 모드: STANDALONE (Host: {}, Port: {})", redisProperties.getHost(), redisProperties.getPort());
        /*
        * Create a new RedisStandaloneConfiguration given hostName and port.
        * */
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort()));
    }

    /**
     * @method      redisTemplate
     * @author      dahyeon
     * @date        2026-02-02
     * @deacription Redis 데이터 조작을 위한 템플릿 설정
    **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());

        // 데이터 정합성을 위해 Spring 트랜잭션(@Transactional)와 연동 설정
        redisTemplate.setEnableTransactionSupport(true);

        // Key는 문자열, Value는 사람이 읽을수 있는 JSON 포맷으로 직렬화
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(new ObjectMapper()));

        return redisTemplate;
    }
}
