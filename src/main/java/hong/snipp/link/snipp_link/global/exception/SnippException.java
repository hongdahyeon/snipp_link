package hong.snipp.link.snipp_link.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * packageName    : hong.snipp.link.snipp_link.global.exception
 * fileName       : SnippException
 * author         : work
 * date           : 2025-04-15
 * description    : 공통 에외 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Getter
public class SnippException extends RuntimeException {

    private final HttpStatus status;

    public SnippException() {
        this("내부 서부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public SnippException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public SnippException(HttpStatus status) {
        this.status = status;
    }

    public SnippException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
