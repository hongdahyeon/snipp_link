package hong.snipp.link.snipp_link.domain.enumcode;

import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.enumcode
 * fileName       : EnumCodeDto
 * author         : work
 * date           : 2025-04-15
 * description    : 코드 공통 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-05-29        work       최초 생성
 * 2026-02-22        home       패키지 구조 변경
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