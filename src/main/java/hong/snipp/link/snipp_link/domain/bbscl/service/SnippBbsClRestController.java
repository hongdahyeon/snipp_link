package hong.snipp.link.snipp_link.domain.bbscl.service;

import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClParam;
import hong.snipp.link.snipp_link.domain.bbscl.dto.response.SnippBbsClList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.service
 * fileName       : SnippBbsClRestController
 * author         : work
 * date           : 2025-05-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-30        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/snipp/api/bbs-cl")
public class SnippBbsClRestController {

    private final SnippBbsClService service;

    /**
     *
     * 분류 정보 트리 형태 조회
     *
     * @api         [GET] /snipp/api/bbs-cl/tree
     * @author      dahyeon
     * @date        2025-05-30
    **/
    @GetMapping("/tree")
    public ResponseEntity clTree(@Valid SnippBbsClParam param) {
        List<SnippBbsClList> snippBbsClLists = service.treeList(param);
        return ResponseEntity.ok(snippBbsClLists);
    }
}
