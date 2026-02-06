# 🏗️ SnippLink 공통 기반 아키텍처 가이드 (Internal Docs)

본 문서는 프로젝트의 생산성과 유지보수성을 높이기 위해 설계된 공통 Bean, Mapper, 응답 규격에 대한 명세서입니다.

---

## 1. 데이터 추적 및 식별 (Audit & ID)

데이터의 생성/수정 정보와 보안을 위한 가상 식별자 관리 클래스입니다.

### 📋 `AuditBean` (Entity 전용)
- **용도**: DB Entity에서 상속받아 작성자(`regUid`)와 수정자(`updtUid`) 정보를 관리합니다.
- **특징**: `setAuditBean(userUid)` 메서드를 통해 생성/수정 시 동일한 사용자 정보를 일괄 주입합니다.

### 📋 `AuditMetaData` (Response DTO 전용)
- **용도**: 클라이언트에 작성자/수정자의 상세 정보(성명, ID, 일시 등)를 응답할 때 사용합니다.
- **특징**: `mainId`가 포함되어 있어 상세 정보와 가상 식별자를 동시에 제공합니다.

### 📋 `ResponseIdBean` (Security & UX)
- **용도**: 모든 응답 DTO의 최상위 부모 클래스로 사용됩니다.
- **특징**:
    - `mainId = UUID.randomUUID()`: 객체 생성 시점에 동적으로 랜덤 ID를 생성합니다.
    - 실제 DB PK를 숨기고 클라이언트와 소통하는 **가상 식별자 규격**을 강제합니다.

---

## 2. 데이터 액세스 표준 (Base Mapper)

MyBatis Mapper 인터페이스의 중복 코드를 줄이고 표준 메서드를 정의합니다.

### 📑 `BaseMapper<T>`
- **특징**: CRUD 및 페이징 처리를 위한 12개의 표준 메서드를 포함합니다.
- **사용법**: 도메인 Mapper 인터페이스에서 상속받아 사용하며, XML에 해당 ID(`insert`, `update`, `page` 등)가 구현되어 있으면 별도 선언 없이 바로 사용 가능합니다.
- **주요 메서드**:
    - `page(param)`: 페이징 쿼리 수행
    - `view(uid)`: 단건 상세 조회
    - `count(param)`: 조건별 데이터 개수 확인

---

## 3. 페이징 및 응답 규격 (Page & Error)

### 📦 `Page<B>` & `Pageable` (Paging System)
- **`Pageable`**: 요청(Request) 규격. `pageNumber`, `size`를 관리하며 `generateMap()`을 통해 MyBatis 쿼리에 필요한 `startNumber`를 자동 계산합니다.
- **`Page<B>`**: 응답(Response) 규격. 데이터 리스트(`list`)와 함께 페이징 블록 계산 로직(`startPage`, `endPage`, `prev`, `next`)을 내포하여 프론트엔드 UI 렌더링을 지원합니다.

### 📦 `ErrorResponse` (Common Error Format)
- **용도**: 시스템 전역에서 발생하는 예외에 대한 공통 JSON 응답 포맷입니다.
- **구성**: `status`(HTTP 상태명), `message`(사용자 메시지), `error`(에러 코드)
- **특징**: `toJson()` 정적 메서드를 통해 필터나 핸들러 단에서 직접 JSON 문자열 응답이 가능합니다.

---

## 4. 실전 활용 사례 (Usage Example)

### (1) AccessDeniedHandler에서의 공통 에러 응답
보안 오류 발생 시 `ErrorResponse`를 사용하여 403 Forbidden 응답을 표준화합니다.
```java
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException deniedException) throws IOException {
    String deniedMessage = deniedException.getMessage();
    // 로그에 어떤 URL에서 어떤 사유로 거부되었는지 상세히 기록
    log.warn("Access Denied: URL={}, Message={}", req.getRequestURI(), deniedMessage);
    if (deniedMessage != null && deniedMessage.toLowerCase().contains("csrf")) {
      log.warn("======================= CSRF Token 관련 보안 오류 발생 =======================");
      this.returnMessage(res, "CSRF 토큰이 유효하지 않거나 누락되었습니다.", "CSRF_ERROR");
    } else {
      log.warn("======================= 접근 권한(Role) 부족 오류 발생 =======================");
      this.returnMessage(res, "해당 리소스에 접근할 권한이 없습니다.", "ACCESS_DENIED");
    }
  }
  
  private void returnMessage(HttpServletResponse res, String message, String error) throws IOException {
    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
    res.setContentType("application/json;charset=UTF-8");
    PrintWriter writer = res.getWriter();
    // ErrorResponse 사용
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, message, error);
    String jsonResponse = ErrorResponse.toJson(errorResponse);
    writer.write(jsonResponse);
    writer.flush();
  }
}
```

---

## 5. 자동 데이터 추적 (MyBatis AOP Interceptor)

로그인한 사용자의 정보를 DB 기록(생성자/수정자)에 자동으로 주입하기 위해 AspectJ를 활용한 AOP 계층을 구축했습니다.

### 🛠️ `MyBatisInterceptorAspect`
- **핵심 기능**: `BaseMapper` 또는 도메인별 `Mapper`에서 실행되는 `insert`, `update`, `delete` 메서드를 실시간으로 감시(Around Advice)합니다.
- **자동 주입 프로세스**:
    1. **인증 확인**: `SecurityContextHolder`에서 현재 로그인한 유저의 `Authentication` 객체를 가져옵니다.
    2. **식별자 추출**: 유저가 인증된 상태라면 해당 유저의 `UID`를, 비인증 상태(예: 초기 가입 등)라면 `0L`을 할당합니다.
    3. **타입 매칭**: 메서드 인자 중 `AuditBean`을 상속받은 객체가 있는지 확인합니다.
    4. **값 설정**: `auditBean.setAuditBean(userUid)`를 호출하여 작성자/수정자 정보를 자동으로 채운 뒤 실제 쿼리를 실행합니다.



### 💡 이 설계의 장점 (Engineering Value)
- **비즈니스 로직 집중**: 서비스 레이어에서 "누가 이 데이터를 남겼는가"에 대한 부가적인 코드를 완전히 제거하여, 순수 비즈니스 로직에만 집중할 수 있습니다.
- **실수 방지 (Human Error Zero)**: 개발자가 실수로 수정자 정보를 누락하더라도, 시스템 차원에서 강제로 데이터를 채워주므로 **데이터 추적성(Traceability)**이 완벽히 보장됩니다.
- **확장성**: `BaseMapper`를 상속받는 모든 새로운 도메인 기능에 별도 설정 없이 즉시 적용됩니다.

---

## 6. 전체 아키텍처 흐름도 (Full Stack Flow)

1. **Request**: 클라이언트가 요청을 보냄 (JWT 포함)
2. **Filter**: `JwtAuthenticationFilter`가 인증 및 세션 생성
3. **Controller/Service**: 비즈니스 로직 수행
4. **AOP (Intercept)**: Mapper 실행 직전 `AuditBean`에 세션 유저 UID 자동 주입
5. **MyBatis**: 최종적으로 작성자/수정자 정보가 포함된 SQL 실행
6. **Response**: `ResponseIdBean`을 통해 가상 ID(`mainId`)가 포함된 JSON 응답

---