package hong.snipp.link.snipp_link.global.session.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperties {

    private String host;
    private int port;
    private Cluster cluster;

    @Data
    public static class Cluster {
        private List<String> nodes;
        public void setNodes(String nodeString) {
            if(nodeString != null && !nodeString.isEmpty()) {
                this.nodes = Arrays.stream(nodeString.split(","))
                                    .map(String::trim)
                                    .collect(Collectors.toList());
            }
        }
    }
}
