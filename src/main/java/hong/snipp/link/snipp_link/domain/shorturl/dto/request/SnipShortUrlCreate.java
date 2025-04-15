package hong.snipp.link.snipp_link.domain.shorturl.dto.request;

import hong.snipp.link.snipp_link.global.annotation.YorN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.dto.request
 * fileName       : SnipShortUrlCreate
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 생성 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnipShortUrlCreate {

    @NotBlank
    private String originUrl;

    @YorN(allowNull = false)
    private String isPublic;

    private List<Long> users = new ArrayList<>();
}
