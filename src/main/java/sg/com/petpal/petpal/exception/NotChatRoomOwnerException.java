package sg.com.petpal.petpal.exception;

public class NotChatRoomOwnerException extends RuntimeException{
    public NotChatRoomOwnerException(Long id) {
        super("Owner with id '" + id + "' is not a chat room owner.");
    }
}
