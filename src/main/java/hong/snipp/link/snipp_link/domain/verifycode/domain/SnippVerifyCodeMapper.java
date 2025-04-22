package hong.snipp.link.snipp_link.domain.verifycode.domain;

import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.verifycode.domain
 * fileName       : SnippVerifyCodeMapper
 * author         : work
 * date           : 2025-04-22
 * description    : 유저에게 발송된 인증 코드 관련 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-22        work       최초 생성
 */
@Mapper
public interface SnippVerifyCodeMapper extends BaseMapper<SnippVerifyCode> {

    int countVerifyCode(Map<String,String> map);

    int updateVerifyCodeExpired(Map<String,String> map);

    int updateUserLastLoginDt(String userEmail);
}
