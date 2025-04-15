package hong.snipp.link.snipp_link.domain.shorturl.domain;

import hong.snipp.link.snipp_link.domain.shorturl.dto.response.SnipShortUrlView;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.domain
 * fileName       : SnipShortUrlMapper
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Mapper
public interface SnipShortUrlMapper extends BaseMapper<SnipShortUrl> {

    SnipShortUrlView getShortURLByOriginURL(String originURL);

    SnipShortUrlView getShortURLByShortURL(String shortURL);

    int countByShortURL(String shortURL);
}
