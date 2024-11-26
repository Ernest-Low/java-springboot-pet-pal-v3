package sg.com.petpal.petpal.dto.pets;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import sg.com.petpal.petpal.annotations.EnumValidator;
import sg.com.petpal.petpal.annotations.PictureValidator;
import sg.com.petpal.petpal.model.Gender;

@Getter
public class PetCreateDTO {

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Gender cannot be blank.")
    @EnumValidator(enumClass = Gender.class, message = "Invalid gender provided.")
    private Gender gender;

    @NotBlank(message = "Age cannot be blank.")
    private Integer age;

    @NotBlank(message = "Is neutered cannot be blank.")
    private boolean isNeutered;

    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @Size(min = 1, message = "At least one picture must be provided.")
    @PictureValidator(message = "Pictures elements cannot be blank.")
    private List<String> pictures;

    @NotBlank(message = "Breed cannot be blank.")
    private String breed;

    @NotBlank(message = "Animal group cannot be blank.")
    private String animalGroup;

    @NotBlank(message = "OwnerId cannot be blank.")
    private Long ownerId;
}
