package sg.com.petpal.petpal.dto.pets;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import sg.com.petpal.petpal.annotations.EnumValidator;
import sg.com.petpal.petpal.annotations.NotBlankIfPresentValidator;
import sg.com.petpal.petpal.annotations.PictureValidator;
import sg.com.petpal.petpal.model.Gender;

@Getter
public class PetUpdateDTO {

    @NotBlankIfPresentValidator(message = "Name cannot be blank.")
    private String name;

    @NotBlankIfPresentValidator(message = "Gender cannot be blank.")
    @EnumValidator(enumClass = Gender.class, message = "Invalid gender provided.")
    private Gender gender;

    @NotBlankIfPresentValidator(message = "Age cannot be blank.")
    private Integer age;

    @NotBlankIfPresentValidator(message = "Is neutered cannot be blank.")
    private boolean isNeutered;

    @NotBlankIfPresentValidator(message = "Description cannot be blank.")
    private String description;

    @NotBlankIfPresentValidator(message = "Pictures cannot be blank.")
    @Size(min = 1, message = "At least one picture must be provided.")
    @PictureValidator(message = "Pictures elements cannot be blank.")
    private List<String> pictures;

    @NotBlankIfPresentValidator(message = "Breed cannot be blank.")
    private String breed;

    @NotBlankIfPresentValidator(message = "Animal group cannot be blank.")
    private String animalGroup;

}
