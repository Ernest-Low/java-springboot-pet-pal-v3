package sg.com.petpal.petpal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// import java.nio.file.AccessDeniedException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JwtEmailNoMatchException extends RuntimeException {
    public JwtEmailNoMatchException() {
        super("Your token mismatches the authorization for your action.");
    }
}
