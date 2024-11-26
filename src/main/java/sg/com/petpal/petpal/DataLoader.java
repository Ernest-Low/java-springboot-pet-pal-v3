package sg.com.petpal.petpal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import sg.com.petpal.petpal.model.ChatMessage;
import sg.com.petpal.petpal.model.ChatRoom;
import sg.com.petpal.petpal.model.Gender;
import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.model.Pet;
import sg.com.petpal.petpal.model.PetData;
import sg.com.petpal.petpal.model.OwnerAuth;
import sg.com.petpal.petpal.repository.ChatMessageRepository;
import sg.com.petpal.petpal.repository.ChatRoomRepository;
import sg.com.petpal.petpal.repository.OwnerRepository;
import sg.com.petpal.petpal.repository.PetDataRepository;
import sg.com.petpal.petpal.repository.PetRepository;
import sg.com.petpal.petpal.utils.ImageUtils;

@Component
public class DataLoader {

    @Value("${data.loader.enabled:false}")
    private boolean isDataLoaderEnabled;

    private final PetRepository petRepository;
    private final PetDataRepository petDataRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageUtils imageUtils;

    public DataLoader(PetRepository petRepository, PetDataRepository petDataRepository,
            ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository,
            OwnerRepository ownerRepository, PasswordEncoder passwordEncoder, ImageUtils imageUtils) {
        this.petRepository = petRepository;
        this.petDataRepository = petDataRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageUtils = imageUtils;
    }

    @PostConstruct
    public void loadData() throws IOException {

        if (isDataLoaderEnabled) {
            deleteAllExistingData();

            List<Owner> newOwners = loadOwnersData(3);
            List<Pet> newPets = loadPetInfo(newOwners);
            List<PetData> petDataList = loadPetDataInfo(newPets);
            ChatRoom newChatRoom = loadChatRoomData(newOwners);
            List<ChatMessage> newChatMessages = loadChatMessagesData(3, newChatRoom, newOwners.get(0));

            // petRepository.saveAll(newPets);
            petDataRepository.saveAll(petDataList);
            chatRoomRepository.save(newChatRoom);
            chatMessageRepository.saveAll(newChatMessages);

            List<Owner> newerOwners = loadNewerOwnersData(3);
            List<Pet> newerPets = loadPetInfo(newerOwners);
            ownerRepository.saveAll(newerOwners);
            petRepository.saveAll(newerPets);

            System.out.println("Data loading completed.");

        } else {
            System.out.println("Data loader is disabled.");
        }

    }

    private void deleteAllExistingData() {
        petRepository.deleteAll();
        petDataRepository.deleteAll();
        chatRoomRepository.deleteAll();
        chatMessageRepository.deleteAll();
    }

    // Create 3 owners
    private List<Owner> loadOwnersData(int quantity) {
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

    private List<Owner> loadNewerOwnersData(int quantity) {
        List<Owner> owners = new ArrayList<>();
        Owner owner = Owner.builder()
                .name("John")
                .areaLocation("NTU Street 1")
                .build();
        OwnerAuth ownerAuth = OwnerAuth.builder()
                .email("john@test.com")
                .password(passwordEncoder.encode("johnpass"))
                .build();
        owner.setOwnerAuth(ownerAuth);
        ownerAuth.setOwner(owner);
        owners.add(owner);

        Owner owner2 = Owner.builder()
                .name("Peter")
                .areaLocation("NTU Street 2")
                .build();
        OwnerAuth ownerAuth2 = OwnerAuth.builder()
                .email("peter@test.com")
                .password(passwordEncoder.encode("peterpass"))
                .build();
        owner2.setOwnerAuth(ownerAuth2);
        ownerAuth2.setOwner(owner2);
        owners.add(owner2);

        Owner owner3 = Owner.builder()
                .name("Potter")
                .areaLocation("NTU Street 2")
                .build();
        OwnerAuth ownerAuth3 = OwnerAuth.builder()
                .email("potter@test.com")
                .password(passwordEncoder.encode("potterpass"))
                .build();
        owner3.setOwnerAuth(ownerAuth3);
        ownerAuth3.setOwner(owner3);
        owners.add(owner3);

        return owners;
    }

    private List<Pet> loadPetInfo(List<Owner> owners) throws IOException {
        List<Pet> pets = new ArrayList<>();

        PetData petData1 = PetData.builder()
                .breed("Golden Retriever")
                .animalGroup("Dog")
                .build();
        List<String> images1 = imageUtils
                .saveImages(Arrays.asList(new String[] { "src/main/resources/static/Cute2.jpg" }));
        Pet pet1 = Pet.builder()
                .name("Buddy")
                .gender(Gender.MALE)
                .age(2)
                .isNeutered(true)
                .pictures(images1)
                .description("Goodest boye")
                .petData(petData1)
                .owner(owners.get(0))
                .build();

        petData1.getPets().add(pet1);
        pets.add(pet1);

        PetData petData2 = PetData.builder()
                .breed("Maltipoo")
                .animalGroup("Dog")
                .build();
        List<String> images2 = imageUtils
                .saveImages(
                        Arrays.asList(new String[] { "src/main/resources/static/Cavalier_King_Charles_Spaniel.jpg" }));
        Pet pet2 = Pet.builder()
                .name("Happy")
                .gender(Gender.FEMALE)
                .age(3)
                .isNeutered(false)
                .pictures(images2)
                .description("Clever Girl")
                .petData(petData2)
                .owner(owners.get(1))
                .build();

        petData2.getPets().add(pet2);
        pets.add(pet2);

        PetData petData3 = PetData.builder()
                .breed("Chow Chow")
                .animalGroup("Dog")
                .build();
        List<String> images3 = imageUtils
                .saveImages(Arrays.asList(new String[] { "src/main/resources/static/Cute_Dog.jpg" }));
        Pet pet3 = Pet.builder()
                .name("Charlie")
                .gender(Gender.MALE)
                .age(2)
                .isNeutered(false)
                .pictures(images3)
                .description("Best Friend")
                .petData(petData3)
                .owner(owners.get(2))
                .build();

        petData3.getPets().add(pet3);
        pets.add(pet3);

        return pets;
    }

    private List<PetData> loadPetDataInfo(List<Pet> pets) {
        List<PetData> petDataList = new ArrayList<>();
        PetData petData4 = PetData.builder()
                .breed("Green Cheek Conure")
                .animalGroup("Parrot")
                .build();
        petDataList.add(petData4);

        PetData petData5 = PetData.builder()
                .breed("Red-sided Eclectus")
                .animalGroup("Parrot")
                .build();
        petDataList.add(petData5);

        return petDataList;
    }

    // Create 1 chat room
    private ChatRoom loadChatRoomData(List<Owner> owners) {
        return ChatRoom.builder()
                .owners(owners)
                .build();
    }

    // Create 3 chat messages for 1 owner, 1 chat room
    private List<ChatMessage> loadChatMessagesData(int quantity, ChatRoom newChatRoom, Owner newOwner) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            ChatMessage newChatMessage = ChatMessage.builder()
                    .createdTimestamp(LocalDateTime.now())
                    .updatedTimestamp(LocalDateTime.now())
                    .message("Chat Message " + i)
                    .owner(newOwner)
                    .chatRoom(newChatRoom)
                    .build();
            chatMessages.add(newChatMessage);
        }
        return chatMessages;
    }
}
