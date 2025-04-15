package hong.snipp.link.snipp_link.domain.code;

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
 */
public enum BbsTp {

    BBS_FAQ("faq", "FAQ"),
    BBS_QNA("qna", "1:1문의"),
    BBS_NOTICE("notice", "공지사항"),
    BBS_BOARD("board", "자유 게시판");

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
}
