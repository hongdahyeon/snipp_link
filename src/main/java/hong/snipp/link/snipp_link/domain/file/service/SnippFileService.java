package hong.snipp.link.snipp_link.domain.file.service;


import hong.snipp.link.snipp_link.domain.code.FileStorageTp;
import hong.snipp.link.snipp_link.domain.file.domain.SnippFile;
import hong.snipp.link.snipp_link.domain.file.domain.SnippFileMapper;
import hong.snipp.link.snipp_link.domain.file.dto.request.SnippFileUpload;
import hong.snipp.link.snipp_link.global.docker.s3.S3Config;
import hong.snipp.link.snipp_link.global.docker.s3.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.file.service
 * fileName       : SnippFileService
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 관련 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class SnippFileService {

    private final SnippFileMapper mapper;
    private final StorageService storageService;
    private final S3Config s3Config;

    private String createFileId() {
        return UUID.randomUUID().toString();
    }

    /**
     * @method      uploadFiles
     * @author      dahyeon
     * @date        2026-02-07
     * @deacription 파일 목록 업로드
    **/
    @Transactional
    public Long uploadFiles( List<MultipartFile> files, Long fileUid ) throws IOException {

        if( files != null && !files.isEmpty() ) {

            if(fileUid == null) {
                fileUid = mapper.generateUid();
            }

            for (MultipartFile file : files) {

                String fileId = this.createFileId();

                String bucket = s3Config.getBucket();
                String endpoint = s3Config.getEndpoints().get(s3Config.getTarget());

                String originalFilename = file.getOriginalFilename();
                String extension = "";
                if ( originalFilename.contains(".") ) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                }
                extension = extension.toLowerCase();

                String fileType = file.getContentType();
                long fileSize = file.getSize();
                String fileNm = String.format("%s.%s", fileId, extension);
                String fileUrl = String.format("%s/%s/%s", endpoint, bucket, fileNm);

                // {file} 업로드
                storageService.uploadFromInputStream(file.getInputStream(), fileSize, fileNm, fileType);

                // insert
                SnippFileUpload uploadDto = SnippFileUpload.builder()
                        .fileId(fileId)
                        .fileNm(fileNm)
                        .orgFileNm(originalFilename)
                        .fileUrl(fileUrl)
                        .filePath(fileUrl)
                        .fileType(fileType)
                        .extension(extension)
                        .storageTp(FileStorageTp.STORAGE_S3.name())
                        .fileSize(fileSize)
                        .build();
                SnippFile bean = new SnippFile(fileUid, uploadDto);
                mapper.insert(bean);
            }
        }

        return fileUid;
    }
}
