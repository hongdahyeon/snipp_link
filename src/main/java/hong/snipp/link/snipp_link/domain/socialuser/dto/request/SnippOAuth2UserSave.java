package hong.snipp.link.snipp_link.domain.socialuser.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.socialuser.dto.request
 * fileName       : SnippOAuth2UserSave
 * author         : work
 * date           : 2025-04-21
 * description    : 소셜 사용자 저장 요청용 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-21        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippOAuth2UserSave {

    private String userId;
    private String userEmail;
    private String userNm;
    private String socialTp;

    @Builder(builderMethodName = "insertOAuth2User")
    public SnippOAuth2UserSave(String userId, String userEmail, String userNm, String socialTp) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userNm = userNm;
        this.socialTp = socialTp;
    }
}
