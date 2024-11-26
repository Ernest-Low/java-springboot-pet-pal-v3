package sg.com.petpal.petpal.exception;

import java.util.UUID;

public class ChatRoomNotFoundException extends RuntimeException {
    public ChatRoomNotFoundException(UUID id) {
        super("Unable to find chat room with id: " + id);
    }
}
