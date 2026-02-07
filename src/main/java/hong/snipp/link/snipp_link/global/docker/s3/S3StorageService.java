package hong.snipp.link.snipp_link.global.docker.s3;

import hong.snipp.link.snipp_link.global.docker.s3.exception.S3StorageException;
import hong.snipp.link.snipp_link.global.exception.SnippException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;

/**
 * packageName    : hong.snipp.link.snipp_link.global.docker.s3
 * fileName       : StorageService
 * author         : work
 * date           : 2026-02-07
 * description    : S3 스토리지를 이용한 스토리지 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-07        work       최초 생성
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    private final S3Client s3Client;

    @Value("${s3-storage.bucket}")
    private String bucketName;

    /**
     * @method      createBucketIfNotExist
     * @author      dahyeon
     * @date        2026-02-06
     * @deacription {Bucket} 없을 경우 새롭게 생성
    **/
    private void createBucketIfNotExist() {
        try {
            /**
             * S3 버킷 존재 여부 및 접근 권한 확인 (HeadBucket API)
             * - 200 OK: 버킷 존재 및 접근 가능
             * - 404 Not Found: 버킷이 존재하지 않음 (NoSuchBucketException 발생)
             * - 403 Forbidden: 접근 권한 없음 (인증 정보 확인 필요)
             * ※ HeadBucket은 응답 본문이 없어 상태 코드로만 성공 여부를 판단함.
             */
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
        } catch (NoSuchBucketException e) {
            log.info(">>>> 버킷({})이 없어 새롭게 생성합니다.", bucketName);
            try {
                s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
            } catch (S3Exception bucketEx) {
                throw new S3StorageException(">>>> 버킷 생성 실패: " + bucketEx.awsErrorDetails().errorMessage());
            }
        }
    }

    /**
     * @method      uploadFromInputStream
     * @author      dahyeon
     * @date        2026-02-06
     * @deacription {inputSteam} 값을 통한 업로드
    **/
    @Override
    public void uploadFromInputStream(InputStream inputStream, long contentLength, String objectName, String contentType) {

        try {

            // 1. 버킷 체크 후 없다면 생성
            this.createBucketIfNotExist();

            // 2. 업로드 요청을 위한 객체 생성
            PutObjectRequest putOb = PutObjectRequest.builder()
                                                    .bucket(bucketName)
                                                    .key(objectName)
                                                    .contentType(contentType)
                                                    .contentLength(contentLength)
                                                    .build();

            // 3. 업로드
            s3Client.putObject(putOb, RequestBody.fromInputStream(inputStream, contentLength));
            log.info(">>>> S3 스토리지 파일 업로드 성공: {}", objectName);

        } catch (S3Exception s3Exception) {
            throw new S3StorageException(">>>> S3 서버 오류 발생: " + s3Exception.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new SnippException("파일 업로드 중 시스템 오류 발생: " + e.getMessage());
        }
    }

    /**
     * @method      downloadFile
     * @author      dahyeon
     * @date        2026-02-06
     * @deacription {objectName} 파일 다운로드
    **/
    @Override
    public InputStream downloadFile(String objectName) {
        try {

            log.info(">>>> S3 파일 다운로드 요청이 들어왔습니다...");
            GetObjectRequest getOb = GetObjectRequest.builder()
                                                    .bucket(bucketName)
                                                    .key(objectName)
                                                    .build();
            return s3Client.getObject(getOb);

        } catch (NoSuchKeyException noKey) {
            log.warn(">>>> 파일이 존재하지 않습니다: {}", objectName);
            throw new S3StorageException(">>>> S3 스토리지에 파일이 존재하지 않습니다: " + objectName);
        } catch (S3Exception S3e) {
            throw new S3StorageException(">>>> 파일 다운로드 중 S3 오류 발생: " + S3e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new SnippException("내부 서버 오류 발생: " + e.getMessage());
        }
    }

    /**
     * @method      isFileExists
     * @author      dahyeon
     * @date        2026-02-06
     * @deacription {objectName} 파일 존재 여부 체크
    **/
    @Override
    public boolean isFileExists(String objectName) {
        try {

            HeadObjectRequest headOb = HeadObjectRequest.builder()
                                                        .bucket(bucketName)
                                                        .key(objectName)
                                                        .build();
            // 파일이 없다면, {NoSuchKeyException} catch 문을 타게 된다.
            s3Client.headObject(headOb);
            return true;

        } catch (NoSuchKeyException noKey) {
            log.warn(">>>> 파일이 존재하지 않습니다: {}", objectName);
            return false;
        } catch (S3Exception S3e) {

            if(S3e.statusCode() == 404) {
                return false;
            }
            throw new S3StorageException(">>>> 파일 확인 중 오류 발생: " + S3e.awsErrorDetails().errorMessage());

        } catch (Exception e) {
            throw new SnippException(">>>> 내부 서버 오류 발생: " + e.getMessage());
        }
    }

    /**
     * @method      removeFile
     * @author      dahyeon
     * @date        2026-02-06
     * @deacription {objectName} 파일 삭제
     *              - 실제 스토리지의 파일이 삭제된다.
    **/
    @Override
    public void removeFile(String objectName) {
        try {

            DeleteObjectRequest delOb = DeleteObjectRequest.builder()
                                                            .bucket(bucketName)
                                                            .key(objectName)
                                                            .build();
            s3Client.deleteObject(delOb);
            log.info(">>>> S3 스토리지 파일 삭제를 성공했습니다: {}", objectName);

        } catch (S3Exception S3e) {
            throw new S3StorageException(">>>> S3 스토리지 내부 파일 삭제 실패: " + objectName);
        } catch (Exception e) {
            throw new SnippException(">>>> 내부 서버 오류 발생: " + e.getMessage());
        }
    }
}
