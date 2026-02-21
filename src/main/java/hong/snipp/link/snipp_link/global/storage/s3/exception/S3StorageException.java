package hong.snipp.link.snipp_link.global.storage.s3.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * packageName    : hong.snipp.link.snipp_link.global.storage.s3.exception
 * fileName       : S3StorageException
 * author         : work
 * date           : 2026-02-07
 * description    : S3 스토리지 관련 exception 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-07        work       최초 생성
 */

@Getter
public class S3StorageException extends RuntimeException {

    private final HttpStatus status;


    public S3StorageException() {
        this("S3 파일 시스템 내부 서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public S3StorageException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public S3StorageException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public S3StorageException(HttpStatus status) {
        this.status = status;
    }
}
