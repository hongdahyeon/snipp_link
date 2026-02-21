package hong.snipp.link.snipp_link.domain.bbscl.dao;

import hong.snipp.link.snipp_link.domain.bbscl.dto.request.SnippBbsClParam;
import hong.snipp.link.snipp_link.domain.bbscl.dto.response.SnippBbsClList;
import hong.snipp.link.snipp_link.domain.bbscl.entity.SnippBbsCl;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.bbscl.dao
 * fileName       : SnippBbsClMapper
 * author         : work
 * date           : 2025-05-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-29        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
 */

@Mapper
public interface SnippBbsClMapper extends BaseMapper<SnippBbsCl> {

    List<SnippBbsClList> getBbsClList(SnippBbsClParam param);
}
