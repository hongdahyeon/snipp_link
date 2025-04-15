package hong.snipp.link.snipp_link.domain.user.service;

import hong.snipp.link.snipp_link.domain.user.domain.SnippUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.service
 * fileName       : SnippUserService
 * author         : work
 * date           : 2025-04-15
 * description    : 유저 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class SnippUserService {

    private final SnippUserMapper mapper;
}
