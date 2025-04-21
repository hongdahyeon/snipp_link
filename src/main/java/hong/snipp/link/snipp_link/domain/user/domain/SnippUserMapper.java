package hong.snipp.link.snipp_link.domain.user.domain;

import hong.snipp.link.snipp_link.domain.user.dto.response.SnippUserView;
import hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

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
 * 2025-04-21        work       * 하단 메소드 추가
 *                              => countByUserEmail : {userEmail} 값을 갖는 유저 카운팅
 *                              => updateLastLoginDtAndPwdFailCntByUserId : {userId} 값에 해당하는 사용자의 최근 로그인 일시 + 비번 실패 횟수 초기화
 *                              => updateLastLoginDtAndPwdFailCntByUserEmail : {userEmail} 값에 해당하는 사용자의 최근 로그인 일시 + 비번 실패 횟수 초기화
 *                              => updatePwdFailCnt : 비번 실패 횟수 업데이트
 *                              => countByUserId : {userId} 값을 갖는 유저 카운팅
 */
@Mapper
public interface SnippUserMapper extends BaseMapper<SnippUser> {

    SnippUserView getUserByUserId(String userId);

    int countByUserEmail(String userEmail);

    int updateLastLoginDtAndPwdFailCntByUserId(String userId);

    int updateLastLoginDtAndPwdFailCntByUserEmail(String userEmail);

    int updatePwdFailCnt(Map<String, Object> params);

    int countByUserId(String userId);
}
