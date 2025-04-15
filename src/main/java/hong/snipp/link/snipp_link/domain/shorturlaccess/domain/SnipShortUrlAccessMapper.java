package hong.snipp.link.snipp_link.domain.shorturlaccess.domain;

import hong.snipp.link.snipp_link.domain.shorturlaccess.dto.response.SnipShortUrlAccessList;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturlaccess.domain
 * fileName       : SnipShortUrlAccessMapper
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Mapper
public interface SnipShortUrlAccessMapper extends BaseMapper<SnipShortUrlAccess> {

    int countByUserAndShortURL(Map<String, Object> map);

    List<SnipShortUrlAccessList> getAccessListByUid(Long shortenUrlUid);
}
