package sg.com.petpal.petpal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sg.com.petpal.petpal.dto.auth.AuthLoginDto;
import sg.com.petpal.petpal.dto.auth.AuthResponseDto;
import sg.com.petpal.petpal.dto.owner.OwnerCreateDTO;
import sg.com.petpal.petpal.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authLogin(@Valid @RequestBody AuthLoginDto dto) {
        AuthResponseDto response = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Create owner
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> createOwner(@Valid @RequestBody OwnerCreateDTO dto) {
        AuthResponseDto response = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
