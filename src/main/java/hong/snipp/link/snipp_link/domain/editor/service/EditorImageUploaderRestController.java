package hong.snipp.link.snipp_link.domain.editor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.editor.service
 * fileName       : EditorImageUploaderRestController
 * author         : work
 * date           : 2025-04-23
 * description    : 이미지 업로더 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-23        work       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/image/api")
public class EditorImageUploaderRestController {

    private final EditorImageUploaderService service;

    /**
     *
     * CK-Editor 이미지 업로더
     *
     * @api         [POST] /snipp/image/api/uploadCkImageFile
     * @author      work
     * @date        2025-04-23
    **/
    @PostMapping(value="/uploadCkImageFile", produces="application/json")
    public Map<String, Object> uploadCkImageFile(@RequestParam("file") MultipartFile multipartFile) {
        return service.ckImageUpload(multipartFile);
    }
}
