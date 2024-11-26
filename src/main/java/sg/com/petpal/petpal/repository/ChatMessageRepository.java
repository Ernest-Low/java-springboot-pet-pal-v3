package sg.com.petpal.petpal.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.com.petpal.petpal.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    
}
