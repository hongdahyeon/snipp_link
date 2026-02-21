package hong.snipp.link.snipp_link.domain.shorturl.access.dao;

import hong.snipp.link.snipp_link.domain.shorturl.access.entity.SnippSUrlAccess;
import hong.snipp.link.snipp_link.domain.shorturl.access.dto.response.SnippSUrlAccessList;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.access.dao
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
 * 2026-02-22        home       패키지 구조 변경
 */
@Mapper
public interface SnippSUrlAccessMapper extends BaseMapper<SnippSUrlAccess> {

    int countByUserAndShortURL(Map<String, Object> map);

    List<SnippSUrlAccessList> getAccessListByUid(Long shortenUrlUid);
}
