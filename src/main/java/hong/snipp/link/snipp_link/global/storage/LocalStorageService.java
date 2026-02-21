package hong.snipp.link.snipp_link.global.storage;

import hong.snipp.link.snipp_link.global.exception.SnippException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * packageName    : hong.snipp.link.snipp_link.global.storage
 * fileName       : LocalStorageService
 * author         : home
 * date           : 2026-02-22
 * description    : 로컬 스토리지 서비스 구현체
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-22        home       최초 생성
 */

@Slf4j
@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "local", matchIfMissing = true)
public class LocalStorageService implements StorageService {

    @Value("${storage.local.path:D:/hongFileDir/snipp_link/storage/}")
    private String localPath;

    public LocalStorageService(
            @Value("${storage.local.path:D:/hongFileDir/snipp_link/storage/}") String localPath
    ) {
        this.localPath = localPath;
        // 저장소 디렉터리 존재 확인 및 생성
        File directory = new File(localPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        log.info(">>>> Local Storage Service 활성화 (Path: {})", localPath);
    }

    /**
     * 1. 업로드: localPath 하위에 objectName으로 저장
     */
    @Override
    public String uploadFromInputStream(InputStream inputStream, long contentLength, String objectName, String contentType) {
        try {
            Path targetPath = Paths.get(localPath, objectName);
            // 상위 디렉터리가 없으면 생성
            Files.createDirectories(targetPath.getParent());
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            log.info(">>>> 로컬 업로드 성공: {}", targetPath.toAbsolutePath());

            // 로컬은 절대 경로 또는 미리 정의된 로컬 접근 URL 값을 반환
            return targetPath.toAbsolutePath().toString();

        } catch (Exception e) {
            throw new SnippException("로컬 파일 업로드 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 2. 다운로드: localPath/objectName 파일을 InputStream으로 반환
     */
    @Override
    public InputStream downloadFile(String objectName) {
        try {
            Path filePath = Paths.get(localPath, objectName);
            if (!Files.exists(filePath)) {
                throw new SnippException("파일을 찾을 수 없습니다: " + objectName);
            }
            return new FileInputStream(filePath.toFile());
        } catch (Exception e) {
            throw new SnippException("로컬 파일 다운로드 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 3. 존재 여부: localPath/objectName 확인
     */
    @Override
    public boolean isFileExists(String objectName) {
        Path filePath = Paths.get(localPath, objectName);
        boolean exists = Files.exists(filePath);
        log.debug(">>>> 로컬 파일 존재 여부 확인 ({}): {}", objectName, exists);
        return exists;
    }

    /**
     * 4. 삭제: localPath/objectName 삭제
     */
    @Override
    public void removeFile(String objectName) {
        try {
            Path filePath = Paths.get(localPath, objectName);
            if (Files.deleteIfExists(filePath)) {
                log.info(">>>> 로컬 파일 삭제 성공: {}", objectName);
            } else {
                log.warn(">>>> 삭제할 파일이 존재하지 않습니다: {}", objectName);
            }
        } catch (Exception e) {
            throw new SnippException("로컬 파일 삭제 중 오류 발생: " + e.getMessage());
        }
    }
}
