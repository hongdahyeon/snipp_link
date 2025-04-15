package hong.snipp.link.snipp_link.global.validator;

import hong.snipp.link.snipp_link.global.annotation.YorN;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * packageName    : hong.snipp.link.snipp_link.global.validator
 * fileName       : YorNValidator
 * author         : work
 * date           : 2025-04-15
 * description    : [@YorN]이 사용된 필드에 대한 validator
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-15        work       최초 생성
 */
public class YorNValidator implements ConstraintValidator<YorN, String> {
    private boolean allowNull;

    @Override
    public void initialize(YorN constraintAnnotation) {
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return allowNull;
        }
        return value.equals("Y") || value.equals("N");
    }
}