package hong.snipp.link.snipp_link.domain.file.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.file.dto.request
 * fileName       : SnippFileParam
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 페이징 조회 요청 파라미터
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 */

@Getter @Setter
public class SnippFileParam {

    @NotNull
    private Long uid;

    private String storageTp;

}
