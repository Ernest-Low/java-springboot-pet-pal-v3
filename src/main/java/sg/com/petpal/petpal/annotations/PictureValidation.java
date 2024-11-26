package sg.com.petpal.petpal.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PictureValidation implements ConstraintValidator<PictureValidator, String[]> {

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        for (String picture : value) {
            if (picture == null || picture.trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }
}