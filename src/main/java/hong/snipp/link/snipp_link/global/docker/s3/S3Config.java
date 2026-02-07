package hong.snipp.link.snipp_link.global.docker.s3;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.global.docker.s3
 * fileName       : S3Config
 * author         : work
 * date           : 2026-02-07
 * description    : S3 스토리지 config 설정
 *                  => S3Client 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-07        work       최초 생성
 */

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "s3-storage")
public class S3Config {

    private String target;
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String region;

    // { minio: endpoint, seaweedfs: endpoint ... }
    // 'endpoints' 하위의 값을 맵 형태로 가져온다
    private Map<String, String> endpoints = new HashMap<>();

    @Bean
    public S3Client s3Client() {

        // 1. target 값에 해당하는 endpoint
        String targetEndpoint = endpoints.get(target);

        // 2. endpoint 체크
        if( targetEndpoint == null || targetEndpoint.isEmpty() ) {
            throw new IllegalArgumentException(
                    "잘못된 s3-storage.target 설정입니다: " + target + ". ( 가능한 값: " + endpoints.keySet() + " )");
        }

        log.info(">>>> 현재 선택한 스토리지: {} ({})", target.toUpperCase(), targetEndpoint);

        // 3. S3Client 생성
        return S3Client.builder()
                    .endpointOverride(URI.create(targetEndpoint)) // AWS 공식 리전이 아닌 로컬 도커 주소(127.0.0.1)로 접속하기 위한 필수 설정
                    /*
                    * 정적 이증 방식 (Static Credentials)을 사용해 클라이언트 생성
                    * - access key: 로그인 id
                    * - secret key: 로그인 pwd
                    * -> static: 메모리에 고정(static) 시켜두고 사용하겠다는 의미
                    * */
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                    .region(Region.of(region))
                    .serviceConfiguration(
                            S3Configuration.builder()
                                    /*
                                    * MinIO, SeaweedFS 같은 오픈 소스 스토리지는 주소를
                                    * - {localhost:9000/bucket} 형태로 사용해야 하기 때문에 반드시 필요한 옵션이다.
                                     * */
                                    .pathStyleAccessEnabled(true)
                                    .build()
                    )
                    .build();
    }
}
