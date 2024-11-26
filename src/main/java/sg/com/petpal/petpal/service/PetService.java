package sg.com.petpal.petpal.service;

import java.io.IOException;
import java.util.List;

// import java.io.IOException;

// import org.springframework.web.multipart.MultipartFile;

import sg.com.petpal.petpal.dto.pets.PetCreateDTO;
import sg.com.petpal.petpal.dto.pets.PetDTO;
import sg.com.petpal.petpal.dto.pets.PetUpdateDTO;
import sg.com.petpal.petpal.dto.pets.PetsDTO;

public interface PetService {

    // void uploadPicture(Long petId, MultipartFile file) throws IOException;

    List<PetsDTO> getAllPets();

    PetDTO getPet(Long id);

    PetDTO createPet(PetCreateDTO dto, String token) throws IOException;

    PetDTO updatePet(Long id, PetUpdateDTO dto, String token) throws IOException;

    void deletePet(Long id, String token);

}
