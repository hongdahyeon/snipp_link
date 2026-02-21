package hong.snipp.link.snipp_link.global.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * packageName    : hong.snipp.link.snipp_link.global.config
 * fileName       : Paths
 * author         : work
 * date           : 2025-04-18
 * description    : 로그인 전/이후 + 권한에 따른 API 정의
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 * 2025-04-22        work       * 기본적으로 모든 api > 로그인 이전 접근 가능
 *                              * 권한 있는 경우
 *                                -> [api] /api/snipp/{패키지명}/{role}/**
 *                                -> [url] /snipp/{패키지명}/{role}/**
 * 2026-02-05        work        /snipp/api/{패키지명}/{role}/** => /api/snipp/{패키지명}/{role}/**
 */
public class Paths {

    public static final String LOGIN = "/login";

    public static final String LOGOUT = "/logout";

    public static final String[] JWT_EXCLUDE_PATTERNS  = {
            "/assets/css/**",    // (예: /assets/css/main.css)
            "/assets/fonts/**",
            "/assets/js/core/**",
            "/assets/src/**",
            "/html/**",
            "/favicon.ico",
            "/login",
            "/loginProc",
            "/api/auth/refresh",
            "/login/force.json"
    };

    public static final AntPathRequestMatcher[] BEFORE_LOGIN = new  AntPathRequestMatcher[]{
             new AntPathRequestMatcher("/")
            ,new AntPathRequestMatcher("/csrf")
            ,new AntPathRequestMatcher("/test")     // todo 추후 삭제
            ,new AntPathRequestMatcher("/login")
            ,new AntPathRequestMatcher("/join**")
            ,new AntPathRequestMatcher("/snipp-short/**")
            ,new AntPathRequestMatcher("/assets/**")
            ,new AntPathRequestMatcher("/login/force.json")
            ,new AntPathRequestMatcher("/api/snipp/**")
            ,new AntPathRequestMatcher("/snipp/**")
            ,new AntPathRequestMatcher("/ckImage/**")
    };

    public static final AntPathRequestMatcher[] ROLE_SUPER = new  AntPathRequestMatcher[]{
             new AntPathRequestMatcher("/api/snipp/**/super/**")
            ,new AntPathRequestMatcher("/snipp/**/super/**")
    };

    public static final AntPathRequestMatcher[] ROLE_MANAGER = new  AntPathRequestMatcher[]{
             new AntPathRequestMatcher("/api/snipp/**/manager/**")
            ,new AntPathRequestMatcher("/snipp/**/manager/**")
    };
}
