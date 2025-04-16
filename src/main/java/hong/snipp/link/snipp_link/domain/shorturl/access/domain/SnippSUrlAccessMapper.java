package hong.snipp.link.snipp_link.domain.shorturl.access.domain;

import hong.snipp.link.snipp_link.domain.shorturl.access.dto.response.SnippSUrlAccessList;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.access.domain
 * fileName       : SnippSUrlAccessMapper
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccess -> SUrlAccess
 */
@Mapper
public interface SnippSUrlAccessMapper extends BaseMapper<SnippSUrlAccess> {

    int countByUserAndShortURL(Map<String, Object> map);

    List<SnippSUrlAccessList> getAccessListByUid(Long shortenUrlUid);
}
