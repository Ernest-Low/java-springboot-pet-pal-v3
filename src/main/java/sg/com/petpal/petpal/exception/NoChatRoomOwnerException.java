package sg.com.petpal.petpal.exception;

public class NoChatRoomOwnerException extends RuntimeException {
    public NoChatRoomOwnerException() {
        super("There must be at least one owner for a chat room.");
    }
}
