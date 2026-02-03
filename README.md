# [Snipp Link]

## 📖 H2 Console 접속 방법 (Local)

로컬 환경에서 H2 Database Console에 접속하는 방법입니다.

- **접속 주소**: [http://localhost:8083/h2-console](http://localhost:8083/h2-console)
- **JDBC URL**: `jdbc:h2:file:~/snipp_link;MODE=MariaDB;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- **User Name**: `sa`
- **Password**: (빈 값)



## ⚙️ Redis 설정
1. redis.conf 파일 내용 copy&paste
2. D드라이브 경로에 redisData 폴더 생성
3. powershell 켜서 해당 레디스 경로로 들어가서
   ex) cd D:\redis-7.0.11-windows\
   .\redis-server.exe redis.conf 입력
4. 관련 설명 



## 📂 프로젝트 구조 및 핵심 기능

본 프로젝트는 도메인형 디렉터리 구조(`domain`, `global`)를 기반으로 하며,   
**하이브리드 로그인 시스템**과 **Redis 세션 전략**을 핵심 아키텍처로 채택하고 있습니다.

### 1. 🔐 하이브리드 인증 시스템 (Hybrid Authentication)
기존의 **폼 로그인 방식**과 최신 **소셜 로그인 방식**을 동시에 지원하여 사용자 접근성을 극대화했습니다.
- **Form Login**: `Spring Security` 표준 폼 로그인 지원.
- **Social Login**: `OAuth2 Client`를 활용한 소셜 연동 (Kakao, Naver, Google).
- **통합 관리**: `PrincipalDetails` 클래스를 통해 일반 유저와 소셜 유저(`OAuth2User`)를 단일 인터페이스로 통합 관리합니다.
  - 소셜 로그인 시, `PrincipalOAuth2UserService`에서 이메일 중복 체크 및 신규/기존 유저 판별 후 자동 회원가입/로그인 처리.

### 2. 🚀 Redis 세션 클러스터링 (Session Management)
서버 간 상태 공유 및 확장성을 위해 세션 저장소를 DB나 메모리가 아닌 **Redis**로 구축했습니다.
- **Spring Session Redis**: `HttpSession`을 투명하게 Redis로 대체하여 관리(`spring-session-data-redis`).
- **장점**: 다중 서버 환경에서도 세션 정합성 유지, 서버 재시작 시에도 로그인 상태 보존.

### 3. 💾 데이터베이스 마이그레이션 (H2 Database)
개발 생산성 및 편의성을 위해 기존 DB 환경에서 **H2 Database**로 마이그레이션했습니다.
- **Local 환경**: 경량화된 H2 DB를 파일 모드(`file:~/snipp_link`)로 구동.
- **웹 콘솔**: `/h2-console` 접속 지원으로 브라우저에서 직관적인 데이터 확인 및 쿼리 실행 가능.

### 4. 도메인별 주요 기능 (Domain Features)
- **ShortUrl**: 긴 URL을 단축하고, 만료일 설정 및 공개/비공개 전환 기능 제공.
- **Board (BBS)**: 공지사항, FAQ, 1:1 문의, 자유게시판 등 다양한 유형의 게시판 지원.
- **Snipp**: 코드 스니펫 관리 및 공유 (현재 소개 페이지 구현).

### 5. 기술 스택 (Tech Stack)
- **Java 17 & Spring Boot 3.4.4**
- **Persistence**: MyBatis, MariaDB (Driver), Redis
- **View & Security**: Thymeleaf, Spring Security 6, OAuth2 Client

