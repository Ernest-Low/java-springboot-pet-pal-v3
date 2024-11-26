package sg.com.petpal.petpal.dto.owner;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import sg.com.petpal.petpal.annotations.NotBlankIfPresentValidator;

@Getter
public class OwnerUpdateDTO {

    @NotBlankIfPresentValidator(message = "Name cannot be blank.")
    private String name;

    @NotBlankIfPresentValidator(message = "Area location cannot be blank.")
    private String areaLocation;

    @Email(message = "Email should be valid.")
    @NotBlankIfPresentValidator(message = "Email cannot be blank.")
    private String email;

    @NotBlankIfPresentValidator(message = "Old password cannot be blank.")
    private String oldPassword;

    @NotBlankIfPresentValidator(message = "New password cannot be blank.")
    private String newPassword;

    @NotBlankIfPresentValidator(message = "Confirm password cannot be blank.")
    private String confirmPassword;
}