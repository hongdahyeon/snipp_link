package hong.snipp.link.snipp_link.global.bean.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * packageName    : hong.snipp.link.snipp_link.global.bean.error
 * fileName       : ErrorResponse
 * author         : work
 * date           : 2025-04-15
 * description    : 에러 발생시, 공통 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Slf4j
@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String status;
    private String message;
    private String error;

    public ErrorResponse(HttpStatus status, String message, String error){
        this.status = status.name();
        this.message = message;
        this.error = error;
    }

    public static String toJson(ErrorResponse errorResponse) {
        String jsonResponse = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResponse = objectMapper.writeValueAsString(errorResponse);
        } catch (Exception e) {
            log.info("======================= Error [toJson]: {} ===========================", e);
            jsonResponse = "{}";
        }
        return jsonResponse;
    }
}
