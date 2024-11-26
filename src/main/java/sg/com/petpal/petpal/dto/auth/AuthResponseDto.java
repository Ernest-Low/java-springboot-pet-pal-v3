package sg.com.petpal.petpal.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDto {

    private final Long id;

    private final String name;

    private final String areaLocation;
    
    private final String email;

    private final String jwtToken;

}
