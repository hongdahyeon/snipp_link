package hong.snipp.link.snipp_link;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
@SpringBootApplication
public class SnippLinkApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(SnippLinkApplication.class, args);
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String serverPort = environment.getProperty("server.port");
		log.info("======================= Server is running on port: {} ===========================", serverPort);;
	}

}
