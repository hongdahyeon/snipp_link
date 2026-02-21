package hong.snipp.link.snipp_link.domain.file.service;


import hong.snipp.link.snipp_link.domain.enumcode.FileStorageTp;
import hong.snipp.link.snipp_link.domain.file.dao.SnippFileMapper;
import hong.snipp.link.snipp_link.domain.file.dto.request.SnippFileParam;
import hong.snipp.link.snipp_link.domain.file.dto.request.SnippFileUpload;
import hong.snipp.link.snipp_link.domain.file.dto.response.SnippFileList;
import hong.snipp.link.snipp_link.domain.file.dto.response.SnippFileView;
import hong.snipp.link.snipp_link.domain.file.entity.SnippFile;
import hong.snipp.link.snipp_link.global.bean.page.Page;
import hong.snipp.link.snipp_link.global.bean.page.Pageable;
import hong.snipp.link.snipp_link.global.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * 2026-02-08        work       파일 페이징, 리스트 조회
 * 2026-02-09        work       {findFileByFileId, deleteFiles} 추가
 * 2026-02-22        home       패키지 구조 변경
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class SnippFileService {

    private final SnippFileMapper mapper;
    private final StorageService storageService;

    /**
     * @method      createFileId
     * @author      dahyeon
     * @date        2026-02-08
     * @deacription {fileId} 생성
    **/
    private String createFileId() {
        return UUID.randomUUID().toString();
    }

    /**
     * @method      uploadFiles
     * @author      dahyeon
     * @date        2026-02-08
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

                String originalFilename = file.getOriginalFilename();
                String extension = "";
                if ( originalFilename.contains(".") ) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                }
                extension = extension.toLowerCase();

                String fileType = file.getContentType();
                long fileSize = file.getSize();
                String fileNm = String.format("%s.%s", fileId, extension);

                // {file} 업로드
                String fileUrl = storageService.uploadFromInputStream(file.getInputStream(), fileSize, fileNm, fileType);

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

    /**
     * @method      findAllFilePageByFileUid
     * @author      dahyeon
     * @date        2026-02-08
     * @deacription 파일 목록 페이징 조회
    **/
    @Transactional(readOnly = true)
    public Page<SnippFileList> findAllFilePageByFileUid(SnippFileParam param, Pageable pageable) {
        List<SnippFileList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findFileListByFileUid
     * @author      dahyeon
     * @date        2026-02-08
     * @deacription 파일 목록 조회 by file_uid
    **/
    @Transactional(readOnly = true)
    public List<SnippFileList> findFileListByFileUid(Long fileUid) {
        return mapper.list(fileUid);
    }

    /**
     * @method      findFileByFileId
     * @author      dahyeon
     * @date        2026-02-09
     * @deacription 파일 조회 by file_id
    **/
    @Transactional(readOnly = true)
    public SnippFileView findFileByFileId(String fileId) {
        return mapper.getFileByFileId(fileId);
    }

    /**
     * @method      deleteFiles
     * @author      dahyeon
     * @date        2026-02-09
     * @deacription 파일 여러건 삭제 + storage 삭제
    **/
    @Transactional
    public void deleteFiles(List<String> fileIds) {
        if(fileIds != null && !fileIds.isEmpty()) {
            for (String fileId: fileIds) {
                SnippFileView fileView = mapper.getFileByFileId(fileId);
                mapper.deleteFileByFileId(fileId);
                try {
                    storageService.removeFile(fileView.getFileNm());
                } catch (Exception e) {
                    log.warn(">>>> S3 스토리지 오류 발생: " + e.getMessage());
                }
            }
        }
    }
}
