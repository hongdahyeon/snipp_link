package hong.snipp.link.snipp_link.domain.bbscl.service;

import hong.snipp.link.snipp_link.domain.bbscl.domain.SnippBbsClMapper;
import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClParam;
import hong.snipp.link.snipp_link.domain.bbscl.dto.response.SnippBbsClList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.service
 * fileName       : SnippBbsClService
 * author         : work
 * date           : 2025-05-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-30        work       최초 생성
 */
@Service
@RequiredArgsConstructor
public class SnippBbsClService {

    private final SnippBbsClMapper mapper;

    /**
     * @method      treeList
     * @author      dahyeon
     * @date        2025-05-30
     * @deacription 분류정보 트리 형태 조회
    **/
    @Transactional(readOnly = true)
    public List<SnippBbsClList> treeList(SnippBbsClParam param) {
        return mapper.getTreeList(param);
    }
}
