package hong.snipp.link.snipp_link.domain.shorturl.log.domain;

import hong.snipp.link.snipp_link.domain.shorturl.log.dto.response.SnippSUrlLogList;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.log.domain
 * fileName       : SnippSUrlLogMapper
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccessLog -> SUrlLog
 * 2026-01-14        work       getAllLogListByShortLink 추가
 */
@Mapper
public interface SnippSUrlLogMapper extends BaseMapper<SnippSUrlLog> {

    List<SnippSUrlLogList> getAllLogListByShortLink(String shortUrl);
}
