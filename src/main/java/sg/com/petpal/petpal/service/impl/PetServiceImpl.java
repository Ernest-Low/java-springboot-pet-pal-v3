package sg.com.petpal.petpal.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sg.com.petpal.petpal.dto.pets.PetCreateDTO;
import sg.com.petpal.petpal.dto.pets.PetDTO;
import sg.com.petpal.petpal.dto.pets.PetUpdateDTO;
import sg.com.petpal.petpal.dto.pets.PetsDTO;
import sg.com.petpal.petpal.exception.EntryNotFoundException;
import sg.com.petpal.petpal.exception.JwtEmailNoMatchException;
import sg.com.petpal.petpal.exception.OwnerNotFoundException;
import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.model.Pet;
import sg.com.petpal.petpal.model.PetData;
import sg.com.petpal.petpal.repository.OwnerRepository;
import sg.com.petpal.petpal.repository.PetDataRepository;
import sg.com.petpal.petpal.repository.PetRepository;
import sg.com.petpal.petpal.security.JwtTokenUtil;
import sg.com.petpal.petpal.service.PetService;
import sg.com.petpal.petpal.utils.ImageUtils;

@Primary
@Service
@Transactional
public class PetServiceImpl implements PetService {

    private final OwnerRepository ownerRepository;
    private final JwtTokenUtil jwtTokenutil;
    private final PetRepository petRepository;
    private final PetDataRepository petDataRepository;
    private final ImageUtils imageUtils;

    public PetServiceImpl(OwnerRepository ownerRepository, JwtTokenUtil jwtTokenutil, PetRepository petRepository,
            PetDataRepository petDataRepository, ImageUtils imageUtils) {
        this.ownerRepository = ownerRepository;
        this.jwtTokenutil = jwtTokenutil;
        this.petRepository = petRepository;
        this.petDataRepository = petDataRepository;
        this.imageUtils = imageUtils;
    }

    @Override
    public List<PetsDTO> getAllPets() {
        List<PetsDTO> petsBasicDTOList = petRepository.findAll().stream()
                .map(pet -> new PetsDTO(
                        pet.getId(),
                        pet.getName(),
                        pet.getGender(),
                        pet.getAge(),
                        pet.getOwner().getAreaLocation(),
                        pet.getPictures().get(0),
                        pet.getPetData().getBreed(),
                        pet.getPetData().getAnimalGroup()))
                .collect(Collectors.toList());

        return petsBasicDTOList;
    };

    @Override
    public PetDTO getPet(Long id) {
        Pet foundPet = petRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id, "Pet"));
        PetDTO responseDTO = new PetDTO(foundPet.getId(), foundPet.getName(), foundPet.getGender(), foundPet.getAge(),
                foundPet.getOwner().getAreaLocation(), foundPet.getOwner().getId(), foundPet.getPictures(),
                foundPet.getPetData().getBreed(), foundPet.getPetData().getAnimalGroup());

        return responseDTO;
    };

    @Override
    public PetDTO createPet(PetCreateDTO petCreateDTO, String token) throws IOException {
        String incomingEmail = jwtTokenutil.extractEmail(token);
        Owner foundOwner = ownerRepository.findById(petCreateDTO.getOwnerId())
                .orElseThrow(() -> new EntryNotFoundException(petCreateDTO.getOwnerId(), "Owner"));

        if (!foundOwner.getOwnerAuth().getEmail().equals(incomingEmail)) {
            throw new JwtEmailNoMatchException();
        }

        PetData petData = petDataRepository
                .findExistingPetData(petCreateDTO.getBreed(), petCreateDTO.getAnimalGroup())
                .orElseGet(() -> petDataRepository.save(
                        PetData.builder()
                                .breed(petCreateDTO.getBreed())
                                .animalGroup(petCreateDTO.getAnimalGroup())
                                .build()));

        // Handle images using imageutils
        List<String> imagePaths = imageUtils.saveImages(petCreateDTO.getPictures());

        Pet pet = Pet.builder()
                .name(petCreateDTO.getName())
                .gender(petCreateDTO.getGender())
                .age(petCreateDTO.getAge())
                .isNeutered(petCreateDTO.isNeutered())
                .pictures(imagePaths)
                .description(petCreateDTO.getDescription())
                .petData(petData)
                .owner(foundOwner)
                .build();

        petData.getPets().add(pet); // Bi-directional
        foundOwner.getPets().add(pet);

        Pet newPet = petRepository.save(pet);
        PetDTO responseDTO = new PetDTO(newPet.getId(), newPet.getName(), newPet.getGender(), newPet.getAge(),
                newPet.getOwner().getAreaLocation(), newPet.getOwner().getId(), newPet.getPictures(),
                newPet.getPetData().getBreed(), newPet.getPetData().getAnimalGroup());

        return responseDTO;
    };

    @Override
    public PetDTO updatePet(Long id, PetUpdateDTO dto, String token) throws IOException {
        String incomingEmail = jwtTokenutil.extractEmail(token);

        Pet foundPet = petRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id, "Pet"));
        Owner foundOwner = petRepository.findOwnerByPetId(id);

        if (foundOwner == null) {
            throw new EntryNotFoundException(id, "Pet");
        }
        if (!foundOwner.getOwnerAuth().getEmail().equals(incomingEmail)) {
            throw new JwtEmailNoMatchException();
        }

        if (dto.getName() != null) {
            foundPet.setName(dto.getName());
        }

        if (dto.getGender() != null) {
            foundPet.setGender((dto.getGender()));
        }

        if (dto.getAge() != null) {
            foundPet.setAge(dto.getAge());

        }
        if (dto.isNeutered()) {
            foundPet.setNeutered(dto.isNeutered());
        }

        if (dto.getDescription() != null) {
            foundPet.setDescription(dto.getDescription());
        }
        if (dto.getPictures() != null) {
            foundPet.setPictures(imageUtils.saveImages(dto.getPictures()));
        }

        // Handling PetData from here
        // Problem is that the breed / animal group are as a group, unique
        // On top of being an optional field in the DTO (all are optional really)
        String newBreed = dto.getBreed();
        String newAnimalGroup = dto.getAnimalGroup();

        // Is there a need to change petData (breed or animalGroup present)
        if (newBreed != null || newAnimalGroup != null) {
            PetData currentPetData = foundPet.getPetData();

            // Get the new combination of breed and animalGroup
            String breedToCheck = newBreed != null ? newBreed : currentPetData.getBreed();
            String animalGroupToCheck = newAnimalGroup != null ? newAnimalGroup : currentPetData.getAnimalGroup();

            // Does this new combination already exist?
            Optional<PetData> existingPetData = petDataRepository.findExistingPetData(breedToCheck,
                    animalGroupToCheck);

            PetData newPetData;
            if (existingPetData.isPresent()) {
                // Already exists, assign it instead
                newPetData = existingPetData.get();
            } else {
                // Doesn't exist, make a new one
                newPetData = PetData.builder()
                        .breed(breedToCheck)
                        .animalGroup(animalGroupToCheck)
                        .build();
                petDataRepository.save(newPetData);
            }

            long count = petRepository.countByPetDataId(currentPetData.getId());
            if (count == 1) {
                petDataRepository.delete(currentPetData);
            }

            foundPet.setPetData(newPetData);
            newPetData.getPets().add(foundPet); // Bi-directional
        }

        Pet newPet = petRepository.save(foundPet);
        PetDTO responseDTO = new PetDTO(newPet.getId(), newPet.getName(), newPet.getGender(), newPet.getAge(),
                newPet.getOwner().getAreaLocation(), newPet.getOwner().getId(), newPet.getPictures(),
                newPet.getPetData().getBreed(), newPet.getPetData().getAnimalGroup());

        return responseDTO;
    };

    @Override
    public void deletePet(Long id, String token) {
        String incomingEmail = jwtTokenutil.extractEmail(token);

        Pet foundPet = petRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id, "Pet"));
        Owner foundOwner = petRepository.findOwnerByPetId(id);
        if (foundOwner == null) {
            throw new OwnerNotFoundException(id);
        }
        if (!foundOwner.getOwnerAuth().getEmail().equals(incomingEmail)) {
            throw new JwtEmailNoMatchException();
        }

        petRepository.delete(foundPet);
    };

}