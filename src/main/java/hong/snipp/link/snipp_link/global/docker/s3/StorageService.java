package hong.snipp.link.snipp_link.global.docker.s3;

import java.io.InputStream;

/**
 * packageName    : hong.snipp.link.snipp_link.global.docker.s3
 * fileName       : StorageService
 * author         : work
 * date           : 2026-02-07
 * description    : 스토리지 서비스 구현을 위한 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-07        work       최초 생성
 */

public interface StorageService {

    /**
     * InputStream을 이용한 업로드
     */
    void uploadFromInputStream( InputStream inputStream, long contentLength,
                                String objectName, String contentType);

    /**
     * 파일 다운로드 (InputStream 반환)
     */
    InputStream downloadFile(String objectName);

    /**
     * 파일 존재 여부 확인
     */
    boolean isFileExists(String objectName);

    /**
     * 파일 삭제
     */
    void removeFile(String objectName);
}
