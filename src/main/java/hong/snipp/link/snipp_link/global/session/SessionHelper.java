package hong.snipp.link.snipp_link.global.session;


import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.global.auth.PrincipalDetails;
import hong.snipp.link.snipp_link.global.auth.dto.SnippSessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.global.session
 * fileName       : SessionHelper
 * author         : work
 * date           : 2025-04-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-02        work       최초 생성
 */

@Component
@RequiredArgsConstructor
public class SessionHelper {

    private final SessionRegistry sessionRegistry;

    public Authentication initializeAuthentication(SnippSessionUser sessionUser) {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(sessionUser::getRole);

        // 이미 인증이 완료된 사용자를 위한 전용 티켓
        // => 기존 세션에 담긴 유저 정보 대신, 최신 데이터가 담긴 유저 정보로 업데이트
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(new PrincipalDetails(sessionUser), null, collection);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public void findSessionAndExpire(SnippSessionUser sessionUser, HttpServletResponse response) throws IOException {
        // {sessionUser}의 살아있는 세션 정보를 모두 조회
        List<SessionInformation> si = sessionRegistry.getAllSessions(new PrincipalDetails(sessionUser), false);
        if (si != null && !si.isEmpty() ) {
            si.get(0).expireNow();
        } else {
            response.sendRedirect("/login?sessionexpired");
        }
    }

    public void createSessionAndEditSecurityContext(Authentication authentication, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
        // {SessionRegistry}와 동기화 처리
        sessionRegistry.registerNewSession(session.getId(), authentication.getPrincipal());
    }
}
