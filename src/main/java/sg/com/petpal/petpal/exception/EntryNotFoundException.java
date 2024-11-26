package sg.com.petpal.petpal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(Long id, String entry) {
        super("Could not find " + entry + " with id: " + id);
    }
}