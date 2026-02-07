package hong.snipp.link.snipp_link.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.code
 * fileName       : EnumCodeDto
 * author         : work
 * date           : 2026-02-08
 * description    : 파일 스토리지 유형 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2026-02-08        work       최초 생성
 */

@Getter
public enum FileStorageTp {

    STORAGE_S3("S3 스토리지"),
    STORAGE_LOCAL("로컬 스토리지");

    private String name;

    FileStorageTp(String name) {
        this.name = name;
    }
}
