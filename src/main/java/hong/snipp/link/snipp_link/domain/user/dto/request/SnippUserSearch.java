package hong.snipp.link.snipp_link.domain.user.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.user.dto.request
 * fileName       : SnippUserSearch
 * author         : work
 * date           : 2025-04-23
 * description    : 유저 목록 조회 요청용 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-23        work       최초 생성
 */
@Getter @Setter
public class SnippUserSearch {

    private String userNm;
    private String userEmail;
}
