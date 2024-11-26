package sg.com.petpal.petpal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.com.petpal.petpal.dto.owner.OwnerBasicDTO;
import sg.com.petpal.petpal.dto.pets.PetDTO;
import sg.com.petpal.petpal.dto.pets.PetsDTO;
import sg.com.petpal.petpal.service.OwnerService;
import sg.com.petpal.petpal.service.PetService;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final OwnerService ownerService;
    private final PetService petService;

    public PublicController(OwnerService ownerService, PetService petService) {
        this.ownerService = ownerService;
        this.petService = petService;
    }

    // Get all owners
    @GetMapping("/owners")
    public ResponseEntity<List<OwnerBasicDTO>> getOwners() {
        List<OwnerBasicDTO> allOwners = ownerService.getAllOwners();
        return ResponseEntity.status(HttpStatus.OK).body(allOwners);

    }

    // Get owner by id
    @GetMapping("/owners/{id}")
    public ResponseEntity<OwnerBasicDTO> getOwner(@PathVariable Long id) {
        OwnerBasicDTO owner = ownerService.getOwner(id);
        return ResponseEntity.status(HttpStatus.OK).body(owner);
    }

    // Get all pets
    @GetMapping("/pets")
    public ResponseEntity<List<PetsDTO>> getPets() {
        List<PetsDTO> allPets = petService.getAllPets();
        return ResponseEntity.status(HttpStatus.OK).body(allPets);

    }

    // Get pet by id
    @GetMapping("/pets/{id}")
    public ResponseEntity<PetDTO> getPet(@PathVariable Long id) {
        PetDTO pet = petService.getPet(id);
        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

}
