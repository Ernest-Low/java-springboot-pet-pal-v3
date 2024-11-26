package sg.com.petpal.petpal.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sg.com.petpal.petpal.dto.pets.PetCreateDTO;
import sg.com.petpal.petpal.dto.pets.PetDTO;
import sg.com.petpal.petpal.dto.pets.PetUpdateDTO;
import sg.com.petpal.petpal.service.PetService;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    // Create pet
    @PostMapping("/new")
    public ResponseEntity<PetDTO> createPet(@RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody PetCreateDTO dto) throws IOException {
        String token = authorizationHeader.substring(7);
        PetDTO response = petService.createPet(dto, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Delete owner
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePet(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long id) {
        String token = authorizationHeader.substring(7);
        petService.deletePet(id, token);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // Update owner
    @PatchMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long id, @RequestBody PetUpdateDTO dto) throws IOException {
        String token = authorizationHeader.substring(7);
        PetDTO owner = petService.updatePet(id, dto, token);
        return ResponseEntity.status(HttpStatus.OK).body(owner);
    }
}
