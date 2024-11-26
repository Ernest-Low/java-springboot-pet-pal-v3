package sg.com.petpal.petpal.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotBlankIfPresentValidaton.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresentValidator {
    String message() default "Field must not be blank or null if present.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}