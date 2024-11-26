package sg.com.petpal.petpal.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PictureValidation.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PictureValidator {
    String message() default "Invalid pictures list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}