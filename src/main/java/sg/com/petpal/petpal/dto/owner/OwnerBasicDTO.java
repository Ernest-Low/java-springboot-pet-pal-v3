package sg.com.petpal.petpal.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerBasicDTO {

    private final Long id;

    private final String name;

    private final String areaLocation;
    
    private final String email;

}
