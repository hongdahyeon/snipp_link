# 🚀 JWT + Redis 기반 통합 인증 및 실시간 세션 제어 전략

본 프로젝트는 무상태(Stateless)인 JWT의 장점을 취하면서도,  
**실시간 세션 무효화(로그아웃)**, **중복 로그인 방지**, **토큰 자동 갱신** 시스템을 구축하기 위해 Redis를 활용한 하이브리드 인증 아키텍처를 설계했습니다.

---

## 1. 인증 및 세션 제어 전략 (Security Design)

### (1) Redis 기반 실시간 인증 장부 (Registry)
* **Single Source of Truth**: Redis를 인증 장부로 활용하여, 서버가 발급한 최신 토큰의 유효성을 매 요청마다 검증합니다.
* **중복 로그인 차단**: `Auth:Access:[userId]` 키에 최신 토큰을 동기화하여, 새로운 로그인 발생 시 이전 기기의 세션을 즉각적으로 '밀어내기' 처리합니다.

### (2) 하이브리드 토큰 추출 규격
* **API 요청 (`/api/**`)**: HTTP Header(`Authorization: Bearer`) 기반 추출을 강제하여 보안 규격을 통일했습니다.
* **페이지 요청 (SSR)**: Thymeleaf 환경의 페이지 이동 및 새로고침 대응을 위해 **Cookie** 기반 추출을 병행합니다.

---

## 2. 핵심 구현 및 트러블슈팅 (Troubleshooting)

### ✅ OAuth2 인증 데이터 정합성 해결
* **문제**: 소셜 로그인 성공 핸들러에서 토큰 생성 시 `userId`가 `null`로 주입되어 인증 필터에서 `UsernameNotFoundException` 발생.
* **원인**: 소셜 로그인은 폼 로그인과 달리 `request.getParameter()`에 ID 정보가 남지 않아 데이터 추출 방식이 달랐기 때문.
* **해결**: `Authentication` 객체에서 `PrincipalDetails`를 직접 캐스팅하여, 서비스 내에서 동적으로 생성한 `userId`(`provider_id`)를 정확히 추출해 토큰에 주입함으로써 정합성을 확보했습니다.

### ✅ 로그아웃 성공 핸들러 (Clear Strategy)
* **로직**: 로그아웃 성공 시 서버측 Redis 장부(`Access/Refresh`)와 클라이언트측 쿠키를 동시에 파기합니다.
* **의의**: JWT의 단점인 '로그아웃 후에도 토큰이 유효한 문제'를 Redis 장부 대조를 통해 물리적으로 차단했습니다.

### ✅ Refresh Token을 통한 Access Token 재발급 전략
* **프로세스**: 401 Unauthorized 발생 시 Ajax 인터셉터가 `/api/auth/refresh`를 호출합니다.
* **검증**: 쿠키의 Refresh Token과 Redis의 장부값이 일치할 경우에만 새 Access Token을 발급하고 Redis를 갱신합니다.
* **SSR 환경의 한계 인지**: 페이지 이동(SSR) 시에는 브라우저가 직접 401 응답을 가로채서 재발급 API를 호출하기 어렵기 때문에, 실제로는 Ajax 기반의 데이터 그리드 조회 시 주로 작동하는 전략임을 명확히 인지하고 구현했습니다.

---

## 3. 주요 API 규격 및 데이터 구조

### [POST] /api/auth/refresh (토큰 재발급)
* **Request**: Cookie (RefreshToken)
* **Response**: Header (New AccessToken), Redis Update

| Redis Key 패턴 | 타입 | 역할 |
| :--- | :---: | :--- |
| `Auth:Access:[userId]` | **String** | 현재 유효한 Access Token 보관 (중복 로그인 대조용) |
| `Auth:Refresh:[userId]` | **String** | 장부 대조용 Refresh Token 보관 |

---

## 4. 응답 규격 통일 (JSON vs HTML)
* **문제**: 세션 만료 시 Grid.js 등에서 JSON 대신 로그인 HTML을 응답받아 `Unexpected token '<'` 에러 발생.
* **해결**: `AuthenticationEntryPoint`에서 `/api/` 경로 요청은 무조건 JSON(401)을 반환하고, 일반 페이지 요청만 `/login`으로 리다이렉트하도록 응답 처리기를 분리했습니다.