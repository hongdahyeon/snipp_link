package hong.snipp.link.snipp_link.global.config;

import hong.snipp.link.snipp_link.global.interceptor.LoggingInterceptor;
import hong.snipp.link.snipp_link.global.interceptor.UserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.concurrent.TimeUnit;

/**
 * packageName    : hong.snipp.link.snipp_link.global.config
 * fileName       : WebConfig
 * author         : work
 * date           : 2025-04-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-23        work       ck-editor 관련 핸들러 설정 추가
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${hong.ckeditor.path}")
    private String ckeditorPath;

    @Value("${hong.ckeditor.pattern}")
    private String ckEditorPattern;

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler(ckEditorPattern)
            .addResourceLocations("file:///" + ckeditorPath);

        registry
            .addResourceHandler("/assets/**")
            .addResourceLocations("classpath:/static/assets/")
            .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
            .resourceChain(true)
            .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor()).excludePathPatterns("/assets/**/*");
        registry.addInterceptor(userInterceptor()).excludePathPatterns("/assets/**/*", "/login", "/loginProc", "/logout", "/error/**/*");
    }
}
