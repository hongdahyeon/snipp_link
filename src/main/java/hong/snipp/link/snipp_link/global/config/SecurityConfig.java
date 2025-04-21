package hong.snipp.link.snipp_link.global.config;

import hong.snipp.link.snipp_link.global.auth.oauth2.PrincipalOAuth2UserService;
import hong.snipp.link.snipp_link.global.handler.CustomAccessDeniedHandler;
import hong.snipp.link.snipp_link.global.handler.CustomAuthenticationHandler;
import hong.snipp.link.snipp_link.global.handler.login.CustomLoginFailureHandler;
import hong.snipp.link.snipp_link.global.handler.login.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.global.config
 * fileName       : SecurityConfig
 * author         : work
 * date           : 2025-04-18
 * description    : 시큐리티 관련 설정
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-18        work       최초 생성
 * 2025-04-21        work       ~ 개발 작업 완료
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomLoginSuccessHandler successHandler;
    private final CustomLoginFailureHandler failureHandler;
    private final CustomAccessDeniedHandler deniedHandler;
    private final CustomAuthenticationHandler authenticationHandler;
    private final PrincipalOAuth2UserService oAuth2UserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * @method      configureAuthorization
     * @author      work
     * @date        2025-03-27
     * @deacription 권한별 접근 권한 설정
     *              {TIP} hasRole("SUPER") : "ROLE_" 접두사를 붙여서 비교를 한다 => 값이 ROLE_SUPER 같이 저장되어 있어야 한다.
     *              {TIP} hasAuthority("ROLE_SUPER") : 정확히 "ROLE_SUPER" 값과 비교를 한다. (접두사 안붙임)
     **/
    private void configureAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth.requestMatchers(Paths.BEFORE_LOGIN).permitAll() // 로그인 전 접근 가능
                .requestMatchers(Paths.PUBLIC_API).permitAll()
                .requestMatchers(Paths.ROLE_SUPER).hasAuthority("ROLE_SUPER") // {ROLE_SUPER} 권한만 접근 가능
                .requestMatchers(Paths.ROLE_MANAGER).hasAnyAuthority("ROLE_SUPER", "ROLE_MANAGER") // {ROLE_SUPER}, {ROLE_MANAGER} 권한만 접근 가능
                .requestMatchers(Paths.AFTER_LOGIN).authenticated() // 로그인 후 접근 가능
                .anyRequest().authenticated(); // 나머지 요청도 로그인 필요
    }

    /**
     * @method      configureHandler
     * @author      work
     * @date        2025-04-01
     * @deacription 403, 401 핸들러 처리
     **/
    private void configureHandler(ExceptionHandlingConfigurer<HttpSecurity> exception) {
        exception
            .accessDeniedHandler(deniedHandler)
            .authenticationEntryPoint(authenticationHandler);
    }

    /**
     * @method      configureCsrf
     * @author      work
     * @date        2025-03-27
     * @deacription CSRF 공격 방어
     *              * CSRF 토큰을 쿠키에 저장
     *              * HttpOnly 속성 값을 {false}로 설정하여 JS에서 CSRF 토큰에 접근할 수 있도록 한다
     **/
    private void configureCsrf(CsrfConfigurer<HttpSecurity> csrfConfigurer) {
        csrfConfigurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        //csrfConfigurer.disable();
    }

    /**
     * @method      configureHeaders
     * @author      work
     * @date        2025-03-27
     * @deacription HTTP 응답 헤더에 대한 보안 설정
     *              * { Customizer.withDefaults() } : XSS 공격을 방어 ( 브라우저의 CSS 보호 기능을 활성화 )
     *              * referrerPolicy : 참조자 정책 => 기본적으로 페이지 간에 { 동일 출처 }에서만 참조 정보를 보낼 수 있게 설정
     **/
    private void configureHeaders(HeadersConfigurer<HttpSecurity> headersConfigurer) {
        headersConfigurer
                .xssProtection(Customizer.withDefaults())
                .referrerPolicy(policyConfig -> policyConfig.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN));
    }

    /**
     * @method      configureFormLogin
     * @author      work
     * @date        2025-03-27
     * @deacription 폼 로그인 설정
     *              * 로그인 성공 및 실패 관련 설정
     **/
    private void configureFormLogin(FormLoginConfigurer<HttpSecurity> formLoginConfigurer) {
        formLoginConfigurer
                .loginPage(Paths.LOGIN)                     // 로그인 페이지 경로
                .successHandler(successHandler)             // 로그인 성공 핸들러
                .failureHandler(failureHandler)             // 로그인 실패 핸들러
                .usernameParameter("userId")                // 사용자 아이디 파라미터 이름
                .loginProcessingUrl("/loginProc");          // 로그인 처리 URL
    }


    /**
     * @method      configureOAuth2Login
     * @author      work
     * @date        2025-03-27
     * @deacription oAuth2 로그인
     **/
    private void configureOAuth2Login(OAuth2LoginConfigurer<HttpSecurity> oauth2LoginConfigurer) {
        oauth2LoginConfigurer
            .loginPage(Paths.LOGIN) // 로그인 페이지 설정
            .successHandler(successHandler) // 로그인 성공 시 처리할 핸들러
            .failureHandler(failureHandler) // 로그인 실패 시 처리할 핸들러
            .userInfoEndpoint(userInfoEndpointConfig ->
                userInfoEndpointConfig.userService(oAuth2UserService) // 사용자 정보 엔드포인트 설정
            );
    }


    /**
     * @method      configureLogout
     * @author      work
     * @date        2025-03-27
     * @deacription 로그아웃 관련 설정
     **/
    private void configureLogout(LogoutConfigurer<HttpSecurity> logoutConfigurer) {
        logoutConfigurer
                .logoutUrl(Paths.LOGOUT)        // 로그아웃 URL 설정
                .logoutSuccessUrl(Paths.LOGIN)  // 로그아웃 성공 후 리다이렉트할 URL 설정
                .invalidateHttpSession(true)    // 세션 무효화
                .clearAuthentication(true);     // 인증 정보 지우기
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(cs -> configureCsrf(cs))
            .cors(corsConfigurer -> corsConfigurer.configurationSource(request -> corsConfiguration()))
            .headers(header -> configureHeaders(header))
            .authorizeHttpRequests(auth -> configureAuthorization(auth))
            .exceptionHandling(ex -> configureHandler(ex))
            .formLogin(form -> configureFormLogin(form))
            .oauth2Login(oauth2LoginConfigurer -> configureOAuth2Login(oauth2LoginConfigurer))
            .logout(logout -> configureLogout(logout));
        return http.build();
    }

    /**
     * @method      corsConfiguration
     * @author      work
     * @date        2025-03-27
     * @deacription CORS 설정
     **/
    private CorsConfiguration corsConfiguration(){
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOriginPatterns(List.of("*"));    // 모든 출처 허용
        cors.setAllowedMethods(List.of("*"));           // 모든 http 메서드 허용
        cors.setAllowedHeaders(List.of("*"));           // 모든 헤더 허용
        cors.setAllowCredentials(true);                     // 자격 증명 허용 => 웹 브라우저가 요청을 보낼 때 쿠키와 HTTP 인증 정보를 함께 보내는지 여부를 결정한다.
        cors.setMaxAge(3600L);                              // pre-flight 요청 캐시 시간 (초)
        return cors;
    }
}
