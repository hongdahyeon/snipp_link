package hong.snipp.link.snipp_link.domain.socialuser.dao;

import hong.snipp.link.snipp_link.domain.socialuser.entity.SnippSocialUser;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.socialuser.dao
 * fileName       : SnippSocialUserMapper
 * author         : work
 * date           : 2025-04-15
 * description    : 소셜 유저 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
 */
@Mapper
public interface SnippSocialUserMapper extends BaseMapper<SnippSocialUser> {
}
