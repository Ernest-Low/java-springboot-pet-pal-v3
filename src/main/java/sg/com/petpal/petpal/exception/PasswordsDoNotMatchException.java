package sg.com.petpal.petpal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Ideally, this scenario should never happen, as frontend should validate it
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException() {
        super("Your confirmed password does not match.");
    }
}