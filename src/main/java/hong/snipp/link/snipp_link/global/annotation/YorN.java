package hong.snipp.link.snipp_link.global.annotation;

import hong.snipp.link.snipp_link.global.validator.YorNValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * packageName    : hong.snipp.link.snipp_link.global.annotation
 * fileName       : YorN
 * author         : work
 * date           : 2025-04-15
 * description    : Y / N 값으로 들어가는 필드에 사용하는 어노테이션 (기본 : null 값을 허용)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
@Documented
@Constraint(validatedBy = YorNValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface YorN {
    String message() default "값은 'Y' 또는 'N'이어야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /** null 허용 여부 (기본값: true) */
    boolean allowNull() default true;
}