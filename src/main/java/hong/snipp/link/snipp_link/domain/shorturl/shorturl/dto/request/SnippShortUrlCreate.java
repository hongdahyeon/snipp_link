package hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.request;

import hong.snipp.link.snipp_link.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.request
 * fileName       : SnippShortUrlCreate
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 생성 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       파일 이름 변경 : snip -> snipp
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippShortUrlCreate {

    @NotBlank
    private String originUrl;

    @YorN(allowNull = false)
    private String isPublic;

    private List<Long> users = new ArrayList<>();
}
