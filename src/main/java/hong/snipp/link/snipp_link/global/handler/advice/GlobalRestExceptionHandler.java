package hong.snipp.link.snipp_link.global.handler.advice;

import hong.snipp.link.snipp_link.global.exception.SnippException;
import hong.snipp.link.snipp_link.global.validator.ValidationErrorProcessor;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.global.handler.advice
 * fileName       : GlobalRestExceptionHandler
 * author         : work
 * date           : 2025-04-15
 * description    : ** API에서 발생하는 예외를 공통적으로 처리
 *                  > RESTful API 영역에서 사용되는 핸들러
 *                  > @ResponseBody가 자동으로 적용되어 JSON 응답을 반환한다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    /**
     * @method      handleDatabaseError
     * @author      work
     * @date        2025-04-15
     * @deacription DB 에러
    **/
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity handleDatabaseError(DataAccessException ex) {
        Map<String, Object> response = new HashMap<>();
        String message = "쿼리 처리 중 오류가 발생했습니다.";
        if (ex.getClass().equals(DuplicateKeyException.class)) {
            message = "중복된 ID가 존재합니다.";
        } else if (ex.getClass().equals(ConstraintViolationException.class)) {
            message = "제약 조건 위반 오류가 발생했습니다.";
        } else if (ex.getClass().equals(DataIntegrityViolationException.class)) {
            message = "데이터 무결성 오류가 발생했습니다.";
        } else if (ex.getClass().equals(EmptyResultDataAccessException.class)) {
            message = "조회된 데이터가 없습니다.";
        } else if (ex.getClass().equals(IncorrectResultSizeDataAccessException.class)) {
            message = "조회된 데이터의 크기가 예상과 다릅니다.";
        } else {
            message = ex.getMessage();
        }
        response.put("message", message);
        response.put("error", ex.getClass().getSimpleName());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    /**
     * @method      handleConstraintViolationException
     * @author      work
     * @date        2025-04-15
     * @deacription @RequestParam, @PathVariable 유효성 검사 실패
    **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        return errors;
    }

    /**
     * @method      handleValidationExceptions
     * @author      work
     * @date        2025-04-15
     * @deacription @Valid, @Validated 에러
    **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ValidationErrorProcessor.processErrors(ex);
    }

    /**
     * @method      snippException
     * @author      work
     * @date        2025-04-15
     * @deacription 기본적인 SnippException 에러를 터트렸을때 타게 되는 핸들러
    **/
    @ExceptionHandler(SnippException.class)
    public ResponseEntity snippException(SnippException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }
}
