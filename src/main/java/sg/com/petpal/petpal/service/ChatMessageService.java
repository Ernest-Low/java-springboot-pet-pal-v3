package sg.com.petpal.petpal.service;

import java.util.List;
import java.util.UUID;

import sg.com.petpal.petpal.dto.ChatMessageOwnerDto;
import sg.com.petpal.petpal.model.ChatMessage;

public interface ChatMessageService {

    List<ChatMessage> findChatRoomAllMessages(UUID chatRoomId);

    ChatMessage findChatMessageById(UUID id);
    
    ChatMessage createChatMessage(UUID chatRoomId, ChatMessageOwnerDto chatMessageOwnerDto);
    
    ChatMessage updateChatMessageById(UUID id, ChatMessageOwnerDto chatMessageOwnerDto);

    void deleteChatMessageById(UUID id);
    
}
