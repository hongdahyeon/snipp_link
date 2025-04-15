package hong.snipp.link.snipp_link.global.config;

import hong.snipp.link.snipp_link.global.interceptor.LoggingInterceptor;
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
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    // TODO : 로그인 구현 이후 작업 진행 //
    /*@Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*registry
                .addResourceHandler(ckImgPattern, summerNotePattern)
                .addResourceLocations("file:///" + ckImagePath, "file:///" + summerNoteImagePath);*/

        registry
                .addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(true) //
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor()).excludePathPatterns("/assets/**/*");

        // TODO : 로그인 구현 이후 작업 진행 //
//        registry.addInterceptor(userInterceptor()).excludePathPatterns("/assets/**/*", "/login", "/loginProc", "/logout", "/error/**/*");
    }
}
