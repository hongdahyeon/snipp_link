package hong.snipp.link.snipp_link.domain.user.domain;

import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.domain
 * fileName       : SnippUserMapper
 * author         : work
 * date           : 2025-04-15
 * description    : 유저 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        work       getUserByUserId 메소드 추가
 */
@Mapper
public interface SnippUserMapper extends BaseMapper<SnippUser> {

    SnippUserView getUserByUserId(String userId);
}
