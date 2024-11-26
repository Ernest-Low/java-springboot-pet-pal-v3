package sg.com.petpal.petpal.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import sg.com.petpal.petpal.dto.ChatRoomOwnersDto;
import sg.com.petpal.petpal.exception.ChatRoomNotFoundException;
import sg.com.petpal.petpal.model.ChatMessage;
import sg.com.petpal.petpal.model.ChatRoom;
import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.model.OwnerAuth;
import sg.com.petpal.petpal.repository.ChatRoomRepository;
import sg.com.petpal.petpal.repository.OwnerRepository;

@SpringBootTest
public class ChatRoomServiceImplTest {

    @InjectMocks
    ChatRoomServiceImpl chatRoomServiceImpl;
    
    @Mock
    ChatRoomRepository chatRoomRepository;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    // private ChatRoom chatRoom;
    // private List<ChatRoom> chatRooms;

    // @BeforeEach
    // public void initInfo() {
    //     chatRoom = constructChatRoom(constructOwners());
    //     chatRooms.add(chatRoom);
    // }

    @Test
    public void findAllChatRoomsTest() {
        ChatRoom chatRoom = constructChatRoom(constructOwners());
        List<ChatRoom> chatRooms = new ArrayList<>();
        chatRooms.add(chatRoom);
        
        when(chatRoomRepository.findAll()).thenReturn(chatRooms);
        List<ChatRoom> foundChatRooms = chatRoomServiceImpl.findAllChatRooms();
        assertEquals(chatRooms, foundChatRooms);
    }

    @Test
    public void findChatRoomByIdTest_success() {
        ChatRoom chatRoom = constructChatRoom(constructOwners());
        List<ChatRoom> chatRooms = new ArrayList<>();
        chatRooms.add(chatRoom);
        
        when(chatRoomRepository.findById(chatRoom.getId())).thenReturn(Optional.of(chatRoom));
        ChatRoom foundChatRoom = chatRoomServiceImpl.findChatRoomById(chatRoom.getId());
        assertEquals(chatRoom, foundChatRoom);
    }

    @Test
    public void findChatRoomById_fail() {
        ChatRoom chatRoom = constructChatRoom(constructOwners());
        
        when(chatRoomRepository.findById(chatRoom.getId())).thenReturn(Optional.empty());
        assertThrows(ChatRoomNotFoundException.class, 
            () -> chatRoomServiceImpl.findChatRoomById(chatRoom.getId()));
    }

    // @Test
    // public void createChatRoom_success() {
    //     List<Owner> owners = constructOwners();
    //     ChatRoom chatRoom = constructChatRoom(owners);

    //     when(ownerRepository.findById(1L)).thenReturn(Optional.of(owners.get(0)));
    //     when(ownerRepository.findById(2L)).thenReturn(Optional.of(owners.get(1)));
    //     when(chatRoomRepository.findById(chatRoom.getId())).thenReturn(Optional.of(chatRoom));
    //     ChatRoom foundChatRoom = chatRoomServiceImpl.createChatRoom(constructChatRoomOwnersDto());

    //     assertEquals(chatRoom, foundChatRoom);
    // }

    private List<Owner> constructOwners() {
        List<Owner> owners = new ArrayList<>();
                Owner owner = Owner.builder()
                .name("Tom")
                .areaLocation("NTU Street 1")
                .build();
        OwnerAuth ownerAuth = OwnerAuth.builder()
                .email("tom@test.com")
                .password(passwordEncoder.encode("tompass"))
                .build();
        owner.setOwnerAuth(ownerAuth);
        ownerAuth.setOwner(owner);
        owners.add(owner);

        Owner owner2 = Owner.builder()
                .name("Dick")
                .areaLocation("NTU Street 2")
                .build();
        OwnerAuth ownerAuth2 = OwnerAuth.builder()
                .email("dick@test.com")
                .password(passwordEncoder.encode("dickpass"))
                .build();
        owner2.setOwnerAuth(ownerAuth2);
        ownerAuth2.setOwner(owner2);
        owners.add(owner2);

        Owner owner3 = Owner.builder()
                .name("Harry")
                .areaLocation("NTU Street 2")
                .build();
        OwnerAuth ownerAuth3 = OwnerAuth.builder()
                .email("harry@test.com")
                .password(passwordEncoder.encode("harrypass"))
                .build();
        owner3.setOwnerAuth(ownerAuth3);
        ownerAuth3.setOwner(owner3);
        owners.add(owner3);
        return owners;
    }

    private ChatRoom constructChatRoom(List<Owner> owners) {
        return ChatRoom.builder()
        .owners(owners)
        .build();
    }

    private ChatRoomOwnersDto constructChatRoomOwnersDto() {
        List<Long> ownerIds = new ArrayList<>();
        ownerIds.add(1L);
        ownerIds.add(2L);
        return new ChatRoomOwnersDto(ownerIds);
    }

    // private List<ChatMessage> constructChatMessages(int quantity, ChatRoom chatRoom, Owner owner) {
    //     List<ChatMessage> chatMessages = new ArrayList<>();
    //     for (int i = 0; i < quantity; i++) {
    //         ChatMessage newChatMessage = ChatMessage.builder()
    //             .createdTimestamp(LocalDateTime.now())
    //             .updatedTimestamp(LocalDateTime.now())
    //             .message("Chat Message " + i)
    //             .owner(owner)
    //             .chatRoom(chatRoom)
    //             .build();
    //         chatMessages.add(newChatMessage);
    //     }
    //     return chatMessages;
    // }

}
