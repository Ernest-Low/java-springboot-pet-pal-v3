package sg.com.petpal.petpal.service;

import java.util.List;
import java.util.UUID;

import sg.com.petpal.petpal.dto.ChatRoomOwnersDto;
import sg.com.petpal.petpal.model.ChatRoom;

public interface ChatRoomService {
    
    List<ChatRoom> findAllChatRooms();

    ChatRoom findChatRoomById(UUID id);

    ChatRoom createChatRoom(ChatRoomOwnersDto chatRoomOwnersDto);

    ChatRoom updateChatRoomById(UUID id, ChatRoomOwnersDto chatRoomOwnersDto);

    void deleteChatRoomById(UUID id);

}
