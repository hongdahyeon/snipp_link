package hong.snipp.link.snipp_link.domain.shorturl.shorturl.domain;

import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response.SnippShortUrlView;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.shorturl.domain
 * fileName       : SnippShortUrlMapper
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       파일 이름 변경 : snip -> snipp
 */
@Mapper
public interface SnippShortUrlMapper extends BaseMapper<SnippShortUrl> {

    SnippShortUrlView getShortURLByOriginURL(String originURL);

    SnippShortUrlView getShortURLByShortURL(String shortURL);

    int countByShortURL(String shortURL);
}
