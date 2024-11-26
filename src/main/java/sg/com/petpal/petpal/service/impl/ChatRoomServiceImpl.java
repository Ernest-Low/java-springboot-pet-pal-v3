package sg.com.petpal.petpal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import sg.com.petpal.petpal.dto.ChatRoomOwnersDto;
import sg.com.petpal.petpal.exception.ChatRoomNotFoundException;
import sg.com.petpal.petpal.exception.NoChatRoomOwnerException;
import sg.com.petpal.petpal.exception.EntryNotFoundException;
import sg.com.petpal.petpal.model.ChatRoom;
import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.repository.ChatRoomRepository;
import sg.com.petpal.petpal.repository.OwnerRepository;
import sg.com.petpal.petpal.service.ChatRoomService;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private ChatRoomRepository chatRoomRepository;
    private OwnerRepository ownerRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, OwnerRepository ownerRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<ChatRoom> findAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    @Override
    public ChatRoom findChatRoomById(UUID id) {
        return chatRoomRepository.findById(id).orElseThrow(() -> new ChatRoomNotFoundException(id));
    }

    @Override
    public ChatRoom createChatRoom(ChatRoomOwnersDto chatRoomOwnersDto) {
        List<Long> chatRoomOwnerIds = chatRoomOwnersDto.getOwnerIds();
        if (chatRoomOwnerIds.size() > 0) {
            List<Owner> chatRoomOwners = findChatRoomOwnersByIds(chatRoomOwnerIds);
            ChatRoom newChatRoom = ChatRoom.builder().owners(chatRoomOwners).build();
            return chatRoomRepository.save(newChatRoom);
        } else {
            throw new NoChatRoomOwnerException();
        }
    }

    @Override
    public ChatRoom updateChatRoomById(UUID id, ChatRoomOwnersDto chatRoomOwnersDto) {
        ChatRoom chatRoomToUpdate = chatRoomRepository.findById(id).orElseThrow(() -> new ChatRoomNotFoundException(id));
        List<Long> chatRoomOwnerIds = chatRoomOwnersDto.getOwnerIds();
        if (chatRoomOwnerIds.size() > 0) {
            chatRoomToUpdate.setOwners(findChatRoomOwnersByIds(chatRoomOwnerIds));
            return chatRoomRepository.save(chatRoomToUpdate);
        } else {
            throw new NoChatRoomOwnerException();
        }
    }

    @Override
    public void deleteChatRoomById(UUID id) {
        chatRoomRepository.deleteById(id);
    }

    private List<Owner> findChatRoomOwnersByIds(List<Long> chatRoomOwnerIds) {
        List<Owner> chatRoomOwners = new ArrayList<>();
        for (int i = 0; i < chatRoomOwnerIds.size(); i++) {
            Long ownerId = chatRoomOwnerIds.get(i);
            Owner chatRoomOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntryNotFoundException(ownerId, "Owner"));
            chatRoomOwners.add(chatRoomOwner);
        }
        return chatRoomOwners;
    }
    
}
