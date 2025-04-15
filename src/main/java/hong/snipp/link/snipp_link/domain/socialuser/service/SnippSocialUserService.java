package hong.snipp.link.snipp_link.domain.socialuser.service;

import hong.snipp.link.snipp_link.domain.socialuser.domain.SnippSocialUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.socialuser.service
 * fileName       : SnippSocialUserService
 * author         : work
 * date           : 2025-04-15
 * description    : 소셜 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class SnippSocialUserService {

    private final SnippSocialUserMapper mapper;
}
