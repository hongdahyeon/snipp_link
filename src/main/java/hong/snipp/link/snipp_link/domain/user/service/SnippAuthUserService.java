package hong.snipp.link.snipp_link.domain.user.service;

import hong.snipp.link.snipp_link.domain.user.domain.SnippUserMapper;
import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.service
 * fileName       : SnippAuthUserService
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
public class SnippAuthUserService {

    private final SnippUserMapper mapper;

    @Transactional
    public SnippUserView findUserByUserId(String userId) {
        return mapper.getUserByUserId(userId);
    }
}
