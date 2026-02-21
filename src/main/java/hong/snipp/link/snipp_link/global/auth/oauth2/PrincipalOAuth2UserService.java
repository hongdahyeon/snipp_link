package hong.snipp.link.snipp_link.global.auth.oauth2;

import hong.snipp.link.snipp_link.domain.codeenum.SocialTp;
import hong.snipp.link.snipp_link.domain.socialuser.dto.request.SnippOAuth2UserSave;
import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.domain.user.service.SnippAuthUserService;
import hong.snipp.link.snipp_link.global.auth.PrincipalDetails;
import hong.snipp.link.snipp_link.global.auth.dto.SnippSessionUser;
import hong.snipp.link.snipp_link.global.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.global.auth
 * fileName       : PrincipalOAuth2UserService
 * author         : work
 * date           : 2025-04-16
 * description    : 소셜 로그인 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-16        work       최초 생성
 * 2025-04-18        work       로직 구성
 * 2025-04-21        work       ~ 개발 작업 완료
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final SnippAuthUserService authUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> userInfo = oAuth2User.getAttributes();

        String userId = "";
        String userEmail = "";
        String userNm = "";
        String socialTp = "";

        switch (provider) {
            case "kakao":
                Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
                userId = provider + "_" + userInfo.get("id");
                userEmail = (String) kakaoAccount.get("email");
                userNm = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");
                socialTp = SocialTp.SOCIAL_KAKAO.name();
                break;

            case "naver":
                Map<String, Object> naverAccount = oAuth2User.getAttribute("response");
                userId = provider + "_" + (String) naverAccount.get("id");
                userEmail = (String) naverAccount.get("email");
                userNm = (String) naverAccount.get("name");
                socialTp = SocialTp.SOCIAL_NAVER.name();
                break;

            case "google":
                userId = provider + "_" + userInfo.get("sub");
                userEmail = (String) userInfo.get("email");
                userNm = (String) userInfo.get("name");
                socialTp = SocialTp.SOCIAL_GOOGLE.name();
                break;
        }

        SnippUserView userVo = authUserService.findUserByUserId(userId);
        if( userVo == null ) {

            int countUser = authUserService.countByUserEmail(userEmail);
            // {userEmail} 값이 이미 있는 경우 -> 해당 이메일 주소로 회원가입 불가능
            if(countUser != 0) {

                throw new OAuth2AuthenticationException(new OAuth2ErrorCustom(OAuth2ErrorCode.socialEmailDuplicate, userEmail, ""));

            } else {

                SnippOAuth2UserSave bean = SnippOAuth2UserSave.insertOAuth2User()
                        .userId(userId)
                        .userEmail(userEmail)
                        .userNm(userNm)
                        .socialTp(socialTp)
                        .build();
                SnippUserView oAuth2UserVo = authUserService.saveOAuth2User(bean);
                SnippSessionUser oAuth2SessionUser = new SnippSessionUser(oAuth2UserVo);
                return new PrincipalDetails(oAuth2SessionUser, userInfo);

            }

        } else {
            if("N".equals(userVo.getIsEnable())) {
                throw new OAuth2AuthenticationException(new OAuth2ErrorCustom(OAuth2ErrorCode.socialEnable, userEmail, userId));
            }

            if("Y".equals(userVo.getIsLocked())) {
                throw new OAuth2AuthenticationException(new OAuth2ErrorCustom(OAuth2ErrorCode.socialLock, userEmail, userId));
            }

            if(!TimeUtil.isXYearAfter(userVo.getLastConnDt(), 1)) {
                throw new OAuth2AuthenticationException(new OAuth2ErrorCustom(OAuth2ErrorCode.socialExpired, userEmail, userId));
            }
        }

        SnippSessionUser sessionUser = new SnippSessionUser(userVo);
        this.customUser(sessionUser);
        return new PrincipalDetails(sessionUser, userInfo);
    }

    private void customUser(SnippSessionUser sessionUser) {

    }
}
