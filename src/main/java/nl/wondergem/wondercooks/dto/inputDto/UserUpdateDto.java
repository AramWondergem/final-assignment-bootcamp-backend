package nl.wondergem.wondercooks.dto.inputDto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
public class UserUpdateDto {


    @NotBlank
    @Email
    public String email;
    public String username;
    public String streetAndNumber;
    public String zipcode;
    public String city;
    public String favoriteColour;
    public String allergies;
    public String allergiesExplanation;

}
