package hong.snipp.link.snipp_link.domain;

import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.domain.user.service.SnippAuthUserService;
import hong.snipp.link.snipp_link.global.auth.dto.SnippSessionUser;
import hong.snipp.link.snipp_link.global.session.SessionHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain
 * fileName       : HomeRestController
 * author         : work
 * date           : 2025-04-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-25        work       최초 생성
 * 2025-02-04        work       force.login
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeRestController {

    private final SnippAuthUserService authUserService;
    private final SessionHelper sessionHelper;

    @GetMapping("/csrf")
    public Map<String, String> getCSRFToken(HttpServletRequest req) {
        CsrfToken csrf = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
        Map<String, String> response = new HashMap<>();
        response.put("token", csrf.getToken());
        return response;
    }

    @PostMapping("/login/force.json")
    public ResponseEntity forceLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        SnippUserView user = authUserService.findUserByUserId(userId);
        SnippSessionUser sessionUser = new SnippSessionUser(user);

        session.removeAttribute(userId);
        sessionHelper.findSessionAndExpire(sessionUser, response);
        Authentication authentication = sessionHelper.initializeAuthentication(sessionUser);
        sessionHelper.createSessionAndEditSecurityContext(authentication, request);

        log.info("======================= Force Login User: {} [Role: {}] ===========================", userId,  sessionUser.getRole());

        response.sendRedirect("/snipp");
        return ResponseEntity.ok().build();
    }
}
