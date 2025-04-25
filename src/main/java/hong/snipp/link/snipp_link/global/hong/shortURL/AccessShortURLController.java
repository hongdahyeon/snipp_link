package hong.snipp.link.snipp_link.global.hong.shortURL;

import hong.snipp.link.snipp_link.domain.code.AccessTp;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.dto.response.SnippShortUrlView;
import hong.snipp.link.snipp_link.domain.shorturl.shorturl.service.SnippShortUrlService;
import hong.snipp.link.snipp_link.domain.shorturl.access.service.SnippSUrlAccessService;
import hong.snipp.link.snipp_link.domain.shorturl.log.dto.request.SnippSUrlLogSave;
import hong.snipp.link.snipp_link.domain.shorturl.log.service.SnippSUrlLogService;
import hong.snipp.link.snipp_link.global.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : hong.snipp.link.snipp_link.global.hong.shortURL
 * fileName       : AccessShortURLController
 * author         : work
 * date           : 2025-04-15
 * description    : {SHORT_URL} 접근 컨트롤러
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 * 2025-04-16        home       * "/snip-short" -> "/snipp-short"
 *                              * 파일 이름 변경
 *                              - snip -> snipp
 *                              - ShortUrlAccess -> SUrlAccess
 *                              - ShortUrlAccessLog -> SUrlLog
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/snipp-short")
public class AccessShortURLController {

    private final SnippShortUrlService shortUrlService;
    private final SnippSUrlAccessService accessService;
    private final SnippSUrlLogService accessLogService;

    @GetMapping("/{encode}")
    public String shortURL(@PathVariable("encode") String encode, HttpServletRequest req) {

        String code = "";
        boolean canAccess = false;

        // 1. {shortURL} 정보 찾기
        SnippShortUrlView view = shortUrlService.findShortURLByShortURL(encode);
        if("Y".equals(view.getIsExpired())) {

            canAccess = false;
            code = AccessTp.ACCESS_TIME_EXPIRED.name();

        } else {

            if("N".equals(view.getIsPublic())) {

                // TODO : 로그인 구현 이후 작업 진행 => {param1}에다가 로그인 유저 UID값 넣기 //
                canAccess = accessService.ifUserCanAccess(0L, encode);
                if(canAccess) code = AccessTp.ACCESS_SUCCESS.name();
                else code = AccessTp.ACCESS_NO_PERMISSION.name();

            } else {

                canAccess = true;
                code = AccessTp.ACCESS_SUCCESS.name();
            }
        }

        // 2. 접근 로그 저장
        String accessIp = WebUtil.getIpAddress(req);
        String accessUserAgent = req.getHeader("User-Agent");
        SnippSUrlLogSave bean = SnippSUrlLogSave.insertShortURLAccessLog()
                .shortUrl(encode)
                .accessIp(accessIp)
                .accessUserAgent(accessUserAgent)
                .accessTp(code)
                .build();
        // TODO : 로그인 구현 이후 작업 진행 => {param1}에다가 로그인 유저 UID값 넣기 //
        // if : user-uid is not null -> bean.setUserUid(userUid)
        accessLogService.saveShortURLAccessLog(bean);

        // 4. 해당 {originURL}로 이동
        if(canAccess) {

            return "redirect:" + view.getOriginUrl();

        } else {
            String queryString = "?errorRedirect="+code;
            return "redirect:/" + queryString;
        }
    }
}
