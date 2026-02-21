package hong.snipp.link.snipp_link.global.storage.s3;

import hong.snipp.link.snipp_link.global.exception.SnippException;
import hong.snipp.link.snipp_link.global.storage.StorageService;
import hong.snipp.link.snipp_link.global.storage.s3.exception.S3StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.net.URI;

/**
 * packageName    : hong.snipp.link.snipp_link.global.storage.s3
 * fileName       : MinIOStorageService
 * author         : home
 * date           : 2026-02-22
 * description    : S3 호환 스토리지 서비스 구현체
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-22        home       최초 생성
 */

@Slf4j
@Service
@ConditionalOnExpression("" +
    "'${storage.type}'.equals('minio') or '${storage.type}'.equals('seaweedfs')" +
"")
public class S3CompatibleStorageService implements StorageService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String storageType;
    private final String endpoint;

    public S3CompatibleStorageService(
            @Value("${storage.type}") String storageType,
            @Value("#{ @environment.getProperty('storage.' + '${storage.type}' + '.endpoint') }") String endpoint,
            @Value("#{ @environment.getProperty('storage.' + '${storage.type}' + '.access-key') }") String accessKey,
            @Value("#{ @environment.getProperty('storage.' + '${storage.type}' + '.secret-key') }") String secretKey,
            @Value("#{ @environment.getProperty('storage.' + '${storage.type}' + '.region') }") String region,
            @Value("#{ @environment.getProperty('storage.' + '${storage.type}' + '.bucket') }") String bucket) {

        this.storageType = storageType;
        this.endpoint = endpoint;
        this.bucketName = bucket;
        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(
                    StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey))
                )
                .region(Region.of(region))
                .serviceConfiguration(
                    S3Configuration.builder().pathStyleAccessEnabled(true).build()
                )
                .build();

        log.info(">>>> [{}] Storage Service 활성화 (Endpoint: {})", storageType.toUpperCase(), endpoint);
    }

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
            log.info(">>>> [{}] 버킷({})이 없어 새롭게 생성합니다.", storageType, bucketName);
            try {
                s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
            } catch (S3Exception bucketEx) {
                throw new S3StorageException(">>>> " + storageType + " 버킷 생성 실패: " + bucketEx.awsErrorDetails().errorMessage());
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
    public String uploadFromInputStream(InputStream inputStream, long contentLength, String objectName, String contentType) {

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
            log.info(">>>> [{}] S3 스토리지 파일 업로드 성공: {}", storageType, objectName);

            // 4. 저장된 경로 URL 반환 (endpoint/bucket/objectName)
            // endpoint 예시: http://127.0.0.1:19000
            return String.format("%s/%s/%s", endpoint, bucketName, objectName);

        } catch (S3Exception s3Exception) {
            throw new S3StorageException(">>>> " + storageType + " S3 서버 오류 발생: " + s3Exception.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new SnippException(">>>> " + storageType + " 파일 업로드 중 시스템 오류 발생: " + e.getMessage());
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

            log.info(">>>> [{}] S3 파일 다운로드 요청이 들어왔습니다...", storageType);
            GetObjectRequest getOb = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectName)
                    .build();
            return s3Client.getObject(getOb);

        } catch (NoSuchKeyException noKey) {
            log.warn(">>>> [{}] 파일이 존재하지 않습니다: {}", storageType, objectName);
            throw new S3StorageException(">>>> S3 스토리지에 파일이 존재하지 않습니다: " + objectName);
        } catch (S3Exception S3e) {
            throw new S3StorageException(">>>> " + storageType + " 파일 다운로드 중 S3 오류 발생: " + S3e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new SnippException(">>>> " + storageType + " 내부 서버 오류 발생: " + e.getMessage());
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
            log.warn(">>>> [{}] 파일이 존재하지 않습니다: {}", storageType, objectName);
            return false;
        } catch (S3Exception S3e) {

            if(S3e.statusCode() == 404) {
                return false;
            }
            throw new S3StorageException(">>>> " + storageType + " 파일 확인 중 오류 발생: " + S3e.awsErrorDetails().errorMessage());

        } catch (Exception e) {
            throw new SnippException(">>>> " + storageType + " 내부 서버 오류 발생: " + e.getMessage());
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
            log.info(">>>> [{}] S3 스토리지 파일 삭제를 성공했습니다: {}", storageType, objectName);

        } catch (S3Exception S3e) {
            throw new S3StorageException(">>>> " + storageType + " S3 스토리지 내부 파일 삭제 실패: " + objectName);
        } catch (Exception e) {
            throw new SnippException(">>>> " + storageType + " 내부 서버 오류 발생: " + e.getMessage());
        }
    }
}
