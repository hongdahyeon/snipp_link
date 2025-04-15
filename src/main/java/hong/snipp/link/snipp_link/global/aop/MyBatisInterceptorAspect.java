package hong.snipp.link.snipp_link.global.aop;

import hong.snipp.link.snipp_link.global.bean.audit.AuditBean;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * packageName    : hong.snipp.link.snipp_link.global.aop
 * fileName       : MyBatisInterceptorAspect
 * author         : work
 * date           : 2025-04-15
 * description    : AOP
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Slf4j
@Aspect         // AOP 를 사용하겠다.
@Component      // @Aspect 어노테이션과 함께 같이 사용해준다 (빈등록)
public class MyBatisInterceptorAspect {

    @Around( "execution(* hong.snipp.link.snipp_link.domain..domain.*Mapper.*insert*(..)) || " +
            "execution(* hong.snipp.link.snipp_link.domain..domain.*Mapper.*update*(..)) || "+
            "execution(* hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper.*insert*(..)) || " +
            "execution(* hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper.*delete*(..)) || " +
            "execution(* hong.snipp.link.snipp_link.global.bean.mapper.BaseMapper.*update*(..))"
    )
    public Object beforeInsertOrUpdate(ProceedingJoinPoint joinPoint) throws Throwable {

        // TODO : 로그인 구현 이후 작업 진행 //
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userUid = (UserUtil.isAuthenticated(authentication)) ?
                ((PrincipalDetails) authentication.getPrincipal()).getUser().getUid() : 0L;*/
        Long userUid = 0L;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof AuditBean) {
                AuditBean auditBean = (AuditBean) arg;
                auditBean.setAuditBean(userUid);
            }
        }

        return joinPoint.proceed();
    }
}
