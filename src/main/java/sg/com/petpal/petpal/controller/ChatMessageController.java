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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.com.petpal.petpal.dto.ChatMessageOwnerDto;
import sg.com.petpal.petpal.model.ChatMessage;
import sg.com.petpal.petpal.service.ChatMessageService;

@RestController
@RequestMapping("/chat/message")
public class ChatMessageController {

    private ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<ChatMessage>> findChatRoomAllMessages(@RequestParam(required = false) UUID chatRoomId) {
        return ResponseEntity.ok(chatMessageService.findChatRoomAllMessages(chatRoomId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatMessage> findChatMessageById(@PathVariable UUID id) {
        return ResponseEntity.ok(chatMessageService.findChatMessageById(id));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<ChatMessage> createChatMessage(@RequestParam UUID chatRoomId, @RequestBody ChatMessageOwnerDto chatMessageOwnerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatMessageService.createChatMessage(chatRoomId, chatMessageOwnerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatMessage> updateChatMessageById(@PathVariable UUID id, @RequestBody ChatMessageOwnerDto chatMessageOwnerDto) {
        return ResponseEntity.ok(chatMessageService.updateChatMessageById(id, chatMessageOwnerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteChatMessageById(@PathVariable UUID id) {
        chatMessageService.deleteChatMessageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
