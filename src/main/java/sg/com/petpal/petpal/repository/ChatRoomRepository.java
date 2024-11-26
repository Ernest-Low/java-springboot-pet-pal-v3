package sg.com.petpal.petpal.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.com.petpal.petpal.model.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    
}
