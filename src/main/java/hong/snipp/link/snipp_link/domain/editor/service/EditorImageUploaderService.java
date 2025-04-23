package hong.snipp.link.snipp_link.domain.editor.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.editor.service
 * fileName       : EditorImageUploaderService
 * author         : work
 * date           : 2025-04-23
 * description    : 이미지 업로더 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-23        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class EditorImageUploaderService {

    @Value("${hong.ckeditor.path}")
    private String ckeditorPath;

    @Value("${hong.ckeditor.start-path}")
    private String startPath;

    /**
     * @method      ckImageUpload
     * @author      work
     * @date        2025-04-23
     * @deacription CK-Editor 이미지 업로더
    **/
    @Transactional
    public Map<String, Object> ckImageUpload(MultipartFile multipartFile) {
        Map<String, Object> params = new HashMap<>();

        String originalFileName = multipartFile.getOriginalFilename(); // 오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid + extension; // 저장될 파일 명

        File targetFile = new File(String.format("%s%s%s", ckeditorPath, File.separator, savedFileName)); // 저장될 외부 파일 경로
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일 저장
            params.put("url", startPath + savedFileName);
            params.put("responseCode", "success");
        }
        catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); // 저장된 파일 삭제
            params.put("responseCode", "error");
            e.printStackTrace();
        }
        return params;
    }

}
