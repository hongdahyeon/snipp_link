package hong.snipp.link.snipp_link.domain.shorturl.log.dto.response;

import hong.snipp.link.snipp_link.domain.enumcode.AccessTp;
import hong.snipp.link.snipp_link.global.bean.ResponseIdBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.snipp.link.snipp_link.domain.shorturl.log.dto.response
 * fileName       : SnippSUrlLogList
 * author         : work
 * date           : 2025-04-15
 * description    : SHORT URL 접근 로그 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccessLog -> SUrlLog
 * 2025-04-25        work       ResponseIdBean extend 받기
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnippSUrlLogList extends ResponseIdBean {

    private Long logUid;
    private String shortUrl;
    private String originUrl;
    private Long userUid;
    private String accessIp;
    private String accessUserAgent;
    private String accessDate;
    private String accessTp;
    private String accessTpStr;
    private boolean accessTpIsSuccess;

    public void setAccessTp(String accessIp) {
        AccessTp tp = AccessTp.getAccessTp(accessIp);
        if(tp != null) {
            this.accessTp = tp.name();
            this.accessTpStr = tp.getDescription();
            this.accessTpIsSuccess = tp.isSuccess();
        }
    }
}
