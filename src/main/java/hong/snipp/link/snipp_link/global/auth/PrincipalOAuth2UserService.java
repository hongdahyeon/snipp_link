package hong.snipp.link.snipp_link.global.auth;

import hong.snipp.link.snipp_link.domain.user.service.SnippAuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.snipp.link.snipp_link.global.auth
 * fileName       : PrincipalOAuth2UserService
 * author         : work
 * date           : 2025-04-16
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final SnippAuthUserService authUserService;

    // TODO : 이어서 진행하기
    /*@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    }*/
}
