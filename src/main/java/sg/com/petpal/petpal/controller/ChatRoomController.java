package sg.com.petpal.petpal.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.com.petpal.petpal.dto.ChatRoomOwnersDto;
import sg.com.petpal.petpal.model.ChatRoom;
import sg.com.petpal.petpal.service.ChatRoomService;

@RestController
@RequestMapping("/chat/room")
public class ChatRoomController {

    private ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<ChatRoom>> findAllChatRooms() {
        return ResponseEntity.ok(chatRoomService.findAllChatRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatRoom> findChatRoomById(@PathVariable UUID id) {
        return ResponseEntity.ok(chatRoomService.findChatRoomById(id));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoomOwnersDto chatRoomOwnersDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomService.createChatRoom(chatRoomOwnersDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatRoom> updateChatRoomById(@PathVariable UUID id, @RequestBody ChatRoomOwnersDto chatRoomOwnersDto) {
        return ResponseEntity.ok(chatRoomService.updateChatRoomById(id, chatRoomOwnersDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteChatRoomById(@PathVariable UUID id) {
        chatRoomService.deleteChatRoomById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
