package sg.com.petpal.petpal.service;

import sg.com.petpal.petpal.dto.owner.OwnerCreateDTO;
import sg.com.petpal.petpal.dto.auth.AuthLoginDto;
import sg.com.petpal.petpal.dto.auth.AuthResponseDto;

public interface AuthService {

    AuthResponseDto register(OwnerCreateDTO dto);

    AuthResponseDto login(AuthLoginDto dto);
}
