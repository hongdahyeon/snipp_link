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
 *                                -> [api] /snipp/api/{패키지명}/{role}/**
 *                                -> [url] /snipp/{패키지명}/{role}/**
 */
public class Paths {

    public static final String LOGIN = "/login";

    public static final String LOGOUT = "/logout";

    public static final AntPathRequestMatcher[] BEFORE_LOGIN = new  AntPathRequestMatcher[]{
             new AntPathRequestMatcher("/")
            ,new AntPathRequestMatcher("/login")
            ,new AntPathRequestMatcher("/join**")
            ,new AntPathRequestMatcher("/snipp-short/**")
            ,new AntPathRequestMatcher("/csrf")
            ,new AntPathRequestMatcher("/assets/**")
            ,new AntPathRequestMatcher("/login/force.json")
            ,new AntPathRequestMatcher("/snipp/api/**")
            ,new AntPathRequestMatcher("/snipp/**")
    };

    public static final AntPathRequestMatcher[] AFTER_LOGIN = new  AntPathRequestMatcher[]{
            // 게시글 저장 같은 경우?
            // 댓글 작성이나?
    };

    public static final AntPathRequestMatcher[] ROLE_SUPER = new  AntPathRequestMatcher[]{
             new AntPathRequestMatcher("/snipp/api/**/super/**")
            ,new AntPathRequestMatcher("/snipp/**/super")
    };

    public static final AntPathRequestMatcher[] ROLE_MANAGER = new  AntPathRequestMatcher[]{
             new AntPathRequestMatcher("/snipp/api/**/manager/**")
            ,new AntPathRequestMatcher("/snipp/**/manager")
    };
}
