package sg.com.petpal.petpal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sg.com.petpal.petpal.dto.auth.AuthLoginDto;
import sg.com.petpal.petpal.dto.auth.AuthResponseDto;
import sg.com.petpal.petpal.dto.owner.OwnerCreateDTO;
import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.model.OwnerAuth;
import sg.com.petpal.petpal.repository.OwnerRepository;
import sg.com.petpal.petpal.security.JwtTokenUtil;
import sg.com.petpal.petpal.service.AuthService;

@Primary
@Service
public class AuthServiceImpl implements AuthService {

        @Autowired
        private AuthenticationManager authenticationManager;

        private final OwnerRepository ownerRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtTokenUtil jwtTokenUtil;

        public AuthServiceImpl(OwnerRepository ownerRepository,
                        PasswordEncoder passwordEncoder,
                        JwtTokenUtil jwtTokenUtil) {

                this.ownerRepository = ownerRepository;
                this.passwordEncoder = passwordEncoder;
                this.jwtTokenUtil = jwtTokenUtil;
        }

        @Override
        public AuthResponseDto register(OwnerCreateDTO dto) {
                String token = jwtTokenUtil.generateToken(dto.getEmail());
                // Map DTO to Owner and OwnerAuth entitys
                Owner owner = Owner.builder()
                                .name(dto.getName())
                                .areaLocation(dto.getAreaLocation())
                                .build();
                OwnerAuth ownerAuth = OwnerAuth.builder()
                                .email(dto.getEmail())
                                .password(passwordEncoder.encode(dto.getPassword()))
                                .build();
                // Set bi-directional relationship
                owner.setOwnerAuth(ownerAuth);
                ownerAuth.setOwner(owner);
                // Save owner (OwnerAuth will be cascaded and saved automatically)
                Owner newOwner = ownerRepository.save(owner);
                // Convert to response DTO
                AuthResponseDto responseDTO = new AuthResponseDto(newOwner.getId(), newOwner.getName(),
                                newOwner.getAreaLocation(), newOwner.getOwnerAuth().getEmail(), token);

                return responseDTO;
        }

        @Override
        public AuthResponseDto login(AuthLoginDto dto) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
                String token = jwtTokenUtil.generateToken(dto.getEmail());
                Owner foundOwner = ownerRepository.findByOwnerAuthEmail(dto.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + dto.getEmail()));
                AuthResponseDto responseDTO = new AuthResponseDto(foundOwner.getId(), foundOwner.getName(),
                                foundOwner.getAreaLocation(),
                                foundOwner.getOwnerAuth().getEmail(), token);

                return responseDTO;
        }
}
