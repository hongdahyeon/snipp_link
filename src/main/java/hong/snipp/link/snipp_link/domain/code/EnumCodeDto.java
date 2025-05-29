package hong.snipp.link.snipp_link.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.code
 * fileName       : EnumCodeDto
 * author         : work
 * date           : 2025-04-15
 * description    : 코드 공통 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-29        work       최초 생성
 */

@Getter
public class EnumCodeDto {

    private String code;
    private String name;

    public EnumCodeDto(String code, String name) {
        this.code = code;
        this.name = name;
    }
}