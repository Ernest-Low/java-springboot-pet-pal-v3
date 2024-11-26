package sg.com.petpal.petpal.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import sg.com.petpal.petpal.dto.ChatMessageOwnerDto;
import sg.com.petpal.petpal.exception.ChatMessageNotFoundException;
import sg.com.petpal.petpal.exception.ChatRoomNotFoundException;
import sg.com.petpal.petpal.exception.NotChatRoomOwnerException;
import sg.com.petpal.petpal.exception.EntryNotFoundException;
import sg.com.petpal.petpal.model.ChatMessage;
import sg.com.petpal.petpal.model.ChatRoom;
import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.repository.ChatMessageRepository;
import sg.com.petpal.petpal.repository.ChatRoomRepository;
import sg.com.petpal.petpal.repository.OwnerRepository;
import sg.com.petpal.petpal.service.ChatMessageService;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private OwnerRepository ownerRepository;
    private ChatRoomRepository chatRoomRepository;
    private ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceImpl(OwnerRepository ownerRepository, 
        ChatRoomRepository chatRoomRepository, 
        ChatMessageRepository chatMessageRepository) {
            this.ownerRepository = ownerRepository;
            this.chatRoomRepository = chatRoomRepository;
            this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public List<ChatMessage> findChatRoomAllMessages(UUID chatRoomId) {
        if (chatRoomId != null) {
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new ChatRoomNotFoundException(chatRoomId));
            return chatRoom.getChatMessages();
        } else {
            return chatMessageRepository.findAll();
        }
    }

    @Override
    public ChatMessage findChatMessageById(UUID id) {
        return chatMessageRepository.findById(id).orElseThrow(() -> new ChatMessageNotFoundException(id));
    }

    @Override
    public ChatMessage createChatMessage(UUID chatRoomId, ChatMessageOwnerDto chatMessageOwnerDto) {
        Long ownerId = chatMessageOwnerDto.getOwnerId();
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new EntryNotFoundException(ownerId, "Owner"));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new ChatRoomNotFoundException(chatRoomId));
        if (!chatRoom.getOwners().contains(owner)) {
            throw new NotChatRoomOwnerException(ownerId);
        }
        ChatMessage chatMessage = ChatMessage.builder()
            .createdTimestamp(LocalDateTime.now())
            .updatedTimestamp(LocalDateTime.now())
            .message(chatMessageOwnerDto.getMessage())
            .owner(owner)
            .chatRoom(chatRoom)
            .build();
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public ChatMessage updateChatMessageById(UUID id, ChatMessageOwnerDto chatMessageOwnerDto) {
        ChatMessage chatMessageToUpdate = chatMessageRepository.findById(id).orElseThrow(() -> new ChatMessageNotFoundException(id));
        chatMessageToUpdate.setUpdatedTimestamp(LocalDateTime.now());
        chatMessageToUpdate.setMessage(chatMessageOwnerDto.getMessage());
        return chatMessageRepository.save(chatMessageToUpdate);
    }

    @Override
    public void deleteChatMessageById( UUID id) {
        chatMessageRepository.deleteById(id);
    }
    
}
