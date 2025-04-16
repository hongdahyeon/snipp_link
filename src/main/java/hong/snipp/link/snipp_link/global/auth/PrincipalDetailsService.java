package hong.snipp.link.snipp_link.global.auth;

import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.domain.user.service.SnippAuthUserService;
import hong.snipp.link.snipp_link.global.auth.dto.SnippSessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.snipp.link.snipp_link.global.auth
 * fileName       : PrincipalDetailsService
 * author         : work
 * date           : 2025-04-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final SnippAuthUserService authUserService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        SnippUserView user = authUserService.findUserByUserId(userId);
        SnippSessionUser sessionUser = new SnippSessionUser(user);
        if(user != null) {
            this.customUser(sessionUser);
            return new PrincipalDetails(sessionUser);
        } else throw new UsernameNotFoundException(userId + " 사용자가 없습니다.");
    }

    public void customUser(SnippSessionUser sessionUser) {

    }

}
