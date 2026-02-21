package hong.snipp.link.snipp_link.domain.codeenum;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.code
 * fileName       : BbsTp
 * author         : work
 * date           : 2025-04-15
 * description    : 게시판 유형 코드
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-22        work       BBS_BOARD -> BBS_FREE
 * 2025-05-29        work       {getDescriptionByCode} 추가
 * 2025-05-30        work       board => free
 */
@Getter
public enum BbsTp {

    BBS_FAQ("faq", "FAQ"),
    BBS_QNA("qna", "1:1문의"),
    BBS_NOTICE("notice", "공지사항"),
    BBS_FREE("free", "자유 게시판");

    private String text;
    private String description;

    BbsTp(String text, String description) {
        this.text = text;
        this.description = description;
    }

    public static boolean isValidCode(String code) {
        for( BbsTp bbsTp : BbsTp.values() ) {
            if(bbsTp.name().equals(code)) {
                return true;
            }
        }
        return  false;
    }

    public static String getDescriptionByCode(String code) {
        for (BbsTp bbsTp : BbsTp.values()) {
            if (bbsTp.name().equals(code)) {
                return bbsTp.description;
            }
        }
        return null; // 또는 "Unknown" 등으로 처리 가능
    }

    public static BbsTp getBbsTpByText(String text) {
        for (BbsTp bbsTp : BbsTp.values()) {
            if (bbsTp.getText().equals(text)) {
                return bbsTp;
            }
        }
        return null; // 또는 "Unknown" 등으로 처리 가능
    }

    public static List<EnumCodeDto> toList() {
        return Arrays.stream(values())
                .map(e -> new EnumCodeDto(e.name(), e.getDescription()))
                .collect(Collectors.toList());
    }

}
