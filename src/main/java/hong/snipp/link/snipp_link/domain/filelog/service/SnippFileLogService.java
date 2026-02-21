package hong.snipp.link.snipp_link.domain.filelog.service;

import hong.snipp.link.snipp_link.domain.filelog.dao.SnippFileLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.filelog.service
 * fileName       : SnippFileLogService
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 로그 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class SnippFileLogService {

    private final SnippFileLogMapper mapper;
}
