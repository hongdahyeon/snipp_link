package hong.snipp.link.snipp_link.global.docker;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * packageName    : hong.snipp.link.snipp_link.global.docker
 * fileName       : DockerComposeRunner
 * author         : work
 * date           : 2026-02-07
 * description    : 애플리케이션 실행 시점에 도커 실행을 위한 runner
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-07        work       최초 생성
 * 2026-02-09        work       runner order 추가
 */

@Slf4j
@Order(1)
@Component
public class DockerComposeRunner implements ApplicationRunner {

    @Value("${spring.docker.use:false}")
    private boolean useDocker;

    @Value("${spring.docker.compose.file}")
    private String composeFilePath;

    @Value("${spring.docker.compose.lifecycle-management:start_and_stop}")
    private String lifecycleManagement;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!useDocker) return;

        File composeFile = new File(composeFilePath);
        if (!composeFile.exists()) {
            log.error(">>>> Docker Compose 파일을 찾을 수 없습니다: {}", composeFile.getAbsolutePath());
            return;
        }

        log.info(">>>> Docker Compose 실행 시도 중...");
        executeDockerCommand(composeFile, new String[]{"docker-compose", "-f", composeFile.getName(), "up", "-d"}, "실행");
    }

    /**
     * @method      stopDocker
     * @author      dahyeon
     * @date        2026-02-06
     * @description 스프링 부트 애플리케이션이 종료될 때 실행
     **/
    @PreDestroy
    public void stopDocker() {
        if (!useDocker || !"start_and_stop".equals(lifecycleManagement)) return;

        log.info(">>>> 애플리케이션 종료: Docker 컨테이너를 중지합니다.");
        File composeFile = new File(composeFilePath);

        executeDockerCommand(composeFile, new String[]{"docker-compose", "-f", composeFile.getName(), "down"}, "종료");
    }

    /**
     * @method      executeDockerCommand
     * @author      dahyeon
     * @date        2026-02-06
     * @description Docker 명령어를 실행하고 결과를 콘솔에 로그 출력
     **/
    private void executeDockerCommand(File composeFile, String[] command, String actionName) {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(composeFile.getParentFile());
        pb.redirectErrorStream(true); // 에러 스트림을 통합하여 확인

        try {
            Process process = pb.start();

            // 실시간 로그 출력
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.debug("Docker-{} Output: {}", actionName, line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info(">>>> Docker Compose {} 완료", actionName);
            } else {
                log.error(">>>> Docker Compose {} 실패 (Exit Code: {})", actionName, exitCode);
            }
        } catch (Exception e) {
            log.error(">>>> Docker {} 중 시스템 오류 발생: {}", actionName, e.getMessage());
        }
    }
}