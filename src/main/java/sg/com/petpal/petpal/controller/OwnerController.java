package sg.com.petpal.petpal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import jakarta.validation.Valid;
// import sg.com.petpal.petpal.dto.owner.OwnerCreateDTO;
import sg.com.petpal.petpal.dto.owner.OwnerBasicDTO;
import sg.com.petpal.petpal.dto.owner.OwnerUpdateDTO;
import sg.com.petpal.petpal.service.OwnerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // ? Create owner (Not required, use register)
    // @PostMapping("/new")
    // public ResponseEntity<OwnerBasicDTO> createOwner(@Valid @RequestBody OwnerCreateDTO dto) {
    //     OwnerBasicDTO response = ownerService.createOwner(dto);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(response);
    // }

    // Delete owner
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOwner(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long id) {
        String token = authorizationHeader.substring(7);
        ownerService.deleteOwner(id, token);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // Update owner
    @PatchMapping("/{id}")
    public ResponseEntity<OwnerBasicDTO> updateOwner(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long id, @RequestBody OwnerUpdateDTO dto) {
        String token = authorizationHeader.substring(7);
        OwnerBasicDTO owner = ownerService.updateOwner(id, dto, token);
        return ResponseEntity.status(HttpStatus.OK).body(owner);
    }

}
