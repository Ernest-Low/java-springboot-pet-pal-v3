package sg.com.petpal.petpal.dto.pets;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sg.com.petpal.petpal.model.Gender;

@Getter
@AllArgsConstructor
public class PetDTO {

    private final Long id;

    private final String name;

    private final Gender gender;

    private final Integer age;

    private final String areaLocation;

    private final Long ownerId;

    private final List<String> pictures;

    private final String breed;

    private final String animalGroup;

}
