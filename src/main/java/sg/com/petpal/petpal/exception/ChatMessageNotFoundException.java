package sg.com.petpal.petpal.exception;

import java.util.UUID;

public class ChatMessageNotFoundException extends RuntimeException {
    public ChatMessageNotFoundException(UUID id) {
        super("Could not find chat message with id: " + id);
    }
}
