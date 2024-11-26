package sg.com.petpal.petpal.dto.pets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sg.com.petpal.petpal.model.Gender;

@Getter
@AllArgsConstructor
public class PetsDTO {

    private final Long id;

    private final String name;

    private final Gender gender;

    private final Integer age;

    private final String areaLocation;

    private final String pictures;

    private final String breed;

    private final String animalGroup;

}
