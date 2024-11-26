package sg.com.petpal.petpal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.com.petpal.petpal.dto.owner.OwnerCreateDTO;
import sg.com.petpal.petpal.dto.owner.OwnerBasicDTO;
import sg.com.petpal.petpal.dto.owner.OwnerUpdateDTO;
import sg.com.petpal.petpal.exception.EntryNotFoundException;
import sg.com.petpal.petpal.exception.PasswordsDoNotMatchException;
import sg.com.petpal.petpal.exception.IncorrectPasswordException;
import sg.com.petpal.petpal.exception.JwtEmailNoMatchException;
import sg.com.petpal.petpal.model.ChatRoom;
import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.model.OwnerAuth;
import sg.com.petpal.petpal.repository.ChatRoomRepository;
import sg.com.petpal.petpal.repository.OwnerRepository;
import sg.com.petpal.petpal.security.JwtTokenUtil;
import sg.com.petpal.petpal.service.OwnerService;

@Primary
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ChatRoomRepository chatRoomRepository;
    private final JwtTokenUtil jwtTokenutil;

    public OwnerServiceImpl(OwnerRepository ownerRepository,
            PasswordEncoder passwordEncoder, ChatRoomRepository chatRoomRepository, JwtTokenUtil jwtTokenutil) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.chatRoomRepository = chatRoomRepository;
        this.jwtTokenutil = jwtTokenutil;
    }

    @Override
    public OwnerBasicDTO createOwner(OwnerCreateDTO ownerCreateDTO) {
        // Map DTO to Owner and OwnerAuth entitys
        Owner owner = Owner.builder()
                .name(ownerCreateDTO.getName())
                .areaLocation(ownerCreateDTO.getAreaLocation())
                .build();
        OwnerAuth ownerAuth = OwnerAuth.builder()
                .email(ownerCreateDTO.getEmail())
                .password(passwordEncoder.encode(ownerCreateDTO.getPassword()))
                .build();
        // Set bi-directional relationship
        owner.setOwnerAuth(ownerAuth);
        ownerAuth.setOwner(owner);
        // Save owner (OwnerAuth will be cascaded and saved automatically)
        Owner newOwner = ownerRepository.save(owner);
        // Convert to response DTO
        OwnerBasicDTO responseDTO = new OwnerBasicDTO(newOwner.getId(), newOwner.getName(),
                newOwner.getAreaLocation(), newOwner.getOwnerAuth().getEmail());

        return responseDTO;
    }

    @Override
    public OwnerBasicDTO getOwner(Long id) {
        Owner foundOwner = ownerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id, "Owner"));
        OwnerBasicDTO responseDTO = new OwnerBasicDTO(id, foundOwner.getName(), foundOwner.getAreaLocation(),
                foundOwner.getOwnerAuth().getEmail());
        return responseDTO;
    };

    @Override
    public List<OwnerBasicDTO> getAllOwners() {
        List<OwnerBasicDTO> ownerBasicDTOList = ownerRepository.findAll().stream()
                .map(owner -> new OwnerBasicDTO(
                        owner.getId(),
                        owner.getName(),
                        owner.getAreaLocation(),
                        owner.getOwnerAuth().getEmail()))
                .collect(Collectors.toList());

        return ownerBasicDTOList;
    };

    @Override
    public OwnerBasicDTO updateOwner(Long id, OwnerUpdateDTO dto, String token) {
        String incomingEmail = jwtTokenutil.extractEmail(token);

        Owner foundOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new EntryNotFoundException(id, "Owner"));

        if (!foundOwner.getOwnerAuth().getEmail().equals(incomingEmail)) {
            throw new JwtEmailNoMatchException();
        }

        if (dto.getName() != null) {
            foundOwner.setName(dto.getName());
        }
        if (dto.getAreaLocation() != null) {
            foundOwner.setAreaLocation(dto.getAreaLocation());
        }
        if (dto.getEmail() != null) {
            foundOwner.getOwnerAuth().setEmail(dto.getEmail());
        }
        if (dto.getOldPassword() != null && dto.getNewPassword() != null && dto.getConfirmPassword() != null) {
            if (!passwordEncoder.matches(dto.getOldPassword(), foundOwner.getOwnerAuth().getPassword())) {
                throw new IncorrectPasswordException();
            }
            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                throw new PasswordsDoNotMatchException();
            }
            foundOwner.getOwnerAuth().setPassword(passwordEncoder.encode(dto.getNewPassword()));

        }

        Owner newOwner = ownerRepository.save(foundOwner);
        OwnerBasicDTO responseDTO = new OwnerBasicDTO(id, newOwner.getName(), newOwner.getAreaLocation(),
                newOwner.getOwnerAuth().getEmail());
        return responseDTO;
    };

    @Override
    public void deleteOwner(Long id, String token) {
        String incomingEmail = jwtTokenutil.extractEmail(token);

        Owner foundOwner = ownerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id, "Owner"));
        if (!foundOwner.getOwnerAuth().getEmail().equals(incomingEmail)) {
            throw new JwtEmailNoMatchException();
        }
        // ! Cleaning the owner from chatrooms
        List<ChatRoom> chatrooms = foundOwner.getChatRooms();
        for (ChatRoom chatroom : chatrooms) {
            chatroom.getOwners().remove(foundOwner);
            chatRoomRepository.save(chatroom);
        }

        ownerRepository.delete(foundOwner);
    };
}
