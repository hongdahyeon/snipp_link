# 🚀 Redis 세션 관리 및 하이브리드 인프라 전략 가이드

본 프로젝트는 분산 환경에서의 세션 정합성을 보장하기 위해 **Spring Session Redis**를 도입하였으며,   
로컬(Standalone)과 운영(Cluster) 환경에 유연하게 대응할 수 있는 하이브리드 인프라 아키텍처를 설계했습니다.

---

## 1. 하이브리드 인프라 전략 (Architecture)

환경에 따라 인프라 구성이 달라지는 점을 고려하여 설정을 객체지향적으로 분리하고,   
환경에 맞는 `ConnectionFactory`를 동적으로 생성합니다.

### (1) RedisProperties (설정 모델)
* `spring.data.redis` 하위 설정을 매핑하는 전용 클래스입니다.
* 운영 환경의 **쉼표(`,`) 구분자 문자열**을 `List<String>`으로 자동 파싱하는 커스텀 Setter를 포함하여 인프라 설정 변경에 유연하게 대응합니다.

### (2) RedisConfig (빈 생성 전략)
* **Standalone 모드**: 로컬 개발 시 단일 Redis 노드(`host`, `port`)에 연결합니다.
* **Cluster 모드**: 운영 환경에서 멀티 노드로 구성된 Redis 클러스터(`nodes`)에 연결합니다.
* **JSON 직렬화**: `GenericJackson2JsonRedisSerializer`를 사용하여 Redis 내 데이터를 가독성 있는 JSON 포맷으로 저장하여 운영 효율성을 높였습니다.

---

## 2. 주요 트러블슈팅 (Troubleshooting)

### ✅ 순환 참조(Circular Dependency) 해결
* **문제**: `RedisIndexedSessionRepository`를 커스텀 정의하는 과정에서 기존 빈을 파라미터로 참조하여 **의존성 순환 고리(Cycle)**가 발생, `BeanCreationException`으로 애플리케이션 기동이 실패함.
* **핵심 해결책**:
  - **의존성 분리**: 자기 자신과 동일한 타입의 빈을 주입받는 대신, 하단에 별도로 정의한 **`@Qualifier("redisTemplate")` 빈을 직접 주입**받도록 설계를 변경했습니다.
  - **직접 인스턴스화**: 주입받은 `redisTemplate`을 인자로 사용하여 `new RedisIndexedSessionRepository(redisTemplate)`를 통해 객체를 직접 생성함으로써 스프링의 빈 생성 순서를 명확히 하고 순환 참조를 원천 차단했습니다.
  - **우선순위 보장**: `@Primary` 어노테이션을 부여하여 스프링 세션의 기본 자동 설정보다 커스텀 설정이 우선적으로 적용되도록 제어했습니다.

### ✅ Placeholder Resolution 에러
* **문제**: `${spring.session.redis.namespace}` 값을 찾지 못해 애플리케이션 기동 실패.
* **원인**: YAML 파일 내 `namespace` 설정 경로와 설정 클래스에서 참조하는 경로의 Depth가 불일치함.
* **해결**: YAML 설정을 `spring.session.redis.namespace` 경로로 정확히 일치시켜 해결했습니다.

### ✅ Standalone vs Cluster 연결 불일치
* **문제**: 로컬 Redis(Standalone 모드)에 클러스터 설정으로 접속 시도 시 `Unable to connect to Redis` 발생.
* **해결**: `cluster.nodes` 설정 존재 여부에 따라 `RedisStandaloneConfiguration(단일)`과 `RedisClusterConfiguration(멀티)`을 분기 처리하는 로직을 검증하여 해결했습니다.

---

## 3. Redis 데이터 구조 (Redis Insight 모니터링)

Spring Session Redis가 생성하는 주요 키들의 역할은 다음과 같습니다.

| Key 패턴 | 타입 | 역할 |
| :--- | :---: | :--- |
| `s:s:sessions:[ID]` | **Hash** | 실제 세션 본체 (유저 권한 및 로그인 정보가 담긴 JSON) |
| `s:s:sessions:expires:[ID]` | **String** | 세션 만료를 감시하는 전용 타이머 키 (TTL 관리) |
| `s:s:expirations:[Time]` | **Set** | 특정 시간에 만료될 세션 ID들을 모아둔 관리 장부 |
| `s:s:index:PRINCIPAL_NAME:[User]` | **Set** | 특정 유저가 보유한 세션 목록 (중복 로그인 제어용 인덱스) |

---

## 4. 설정 예시 (application-local.yml)

```yaml
spring:
  session:
    store-type: redis
    timeout: 1h
    redis:
      namespace: spring:session:snipp_link
  data:
    redis:
      host: localhost
      port: 6379
      # cluster:
      #   nodes: 172.30.1.1:6379, 172.30.1.2:6379 (운영 시 사용)