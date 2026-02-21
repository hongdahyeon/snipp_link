package hong.snipp.link.snipp_link.domain.bbscl.service;

import hong.snipp.link.snipp_link.domain.bbscl.entity.SnippBbsCl;
import hong.snipp.link.snipp_link.domain.bbscl.dao.SnippBbsClMapper;
import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClChange;
import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClParam;
import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClSave;
import hong.snipp.link.snipp_link.domain.bbscl.dto.response.SnippBbsClList;
import hong.snipp.link.snipp_link.domain.bbscl.dto.response.SnippBbsClTree;
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
     * @method      findBbsClList
     * @author      dahyeon
     * @date        2025-05-30
     * @deacription 분류정보 리스트 형태 조회
    **/
    @Transactional(readOnly = true)
    public List<SnippBbsClList> findBbsClList(SnippBbsClParam param) {
        return mapper.getBbsClList(param);
    }

    /**
     * @method      findBbsClTree
     * @author      dahyeon
     * @date        2025-05-30
     * @deacription 분류 정보 리스트로 조회 => 트리 형태 변환하여 리턴
    **/
    @Transactional(readOnly = true)
    public List<SnippBbsClTree> findBbsClTree(SnippBbsClParam param) {
        List<SnippBbsClList> bbsClList = mapper.getBbsClList(param);
        List<SnippBbsClTree> bbsClTrees = SnippBbsClTree.buildTree(bbsClList);
        return bbsClTrees;
    }


    /**
     * @method      saveBbsCl
     * @author      dahyeon
     * @date        2025-05-30
     * @deacription 게시판 분류 정보 저장
    **/
    @Transactional
    public void saveBbsCl(SnippBbsClSave request) {
        mapper.insert(new SnippBbsCl(request));
    }

    /**
     * @method      changeBbsCl
     * @author      dahyeon
     * @date        2025-05-30
     * @deacription 게시판 분류 정보 수정
    **/
    @Transactional
    public void changeBbsCl(Long uid, SnippBbsClChange request) {
        SnippBbsCl bean = mapper.view(uid);
        mapper.update(bean.changeBbsCl(uid, request));
    }

    /**
     * @method      deleteBbsCl
     * @author      dahyeon
     * @date        2025-05-30
     * @deacription 게시판 분류 정보 삭제
    **/
    @Transactional
    public void deleteBbsCl(Long uid) {
        mapper.delete(uid);
    }
}
