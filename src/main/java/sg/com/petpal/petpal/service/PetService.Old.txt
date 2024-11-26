package sg.com.petpal.petpal.service;

import sg.com.petpal.petpal.model.Pet;
import sg.com.petpal.petpal.repository.PetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private PetRepository petRepository;

    // Upload picture for a pet
    public void uploadPicture(Long petId, MultipartFile file) throws IOException {
        // Ensure that the pet exists
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));

        // Ensure the uploads directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();  // Create the directory if it doesn't exist
        }

        // Save the file
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.write(path, file.getBytes());

        // Add the path to the pet's pictures list
        if (pet.getPictures() == null) {
            pet.setPictures(new ArrayList<>());
        }
        pet.getPictures().add(path.toString());

        // Save updated pet information
        petRepository.save(pet);
    }

    // Find all pets
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    // Find pet by id
    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    // Create new pet
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    // Update existing pet
    public Pet update(Long id, Pet updatedPet) {
        return petRepository.findById(id).map(pet -> {
            pet.setName(updatedPet.getName());
            pet.setGender(updatedPet.getGender());
            pet.setAge(updatedPet.getAge());
            pet.setNeutered(updatedPet.isNeutered());
            pet.setPictures(updatedPet.getPictures());
            pet.setDescription(updatedPet.getDescription());
            pet.setOwner(updatedPet.getOwner());
            pet.setPetData(updatedPet.getPetData());
            return petRepository.save(pet);
        }).orElseGet(() -> {
            updatedPet.setId(id);
            return petRepository.save(updatedPet);
        });
    }

    // Delete pet by id
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}
