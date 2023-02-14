package nl.wondergem.wondercooks.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Getter
@Setter
@Validated
public class UserInputDto {

    public String username;
    @NotBlank
    @Email
    public String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[\\!\\#\\@\\$\\%\\&\\/\\(\\)\\=\\?\\*\\-\\+\\-\\_\\.\\:\\;\\,\\]\\[\\{\\}\\^])[A-Za-z0-9!#%]{8,32}", message = "Password should have. 1. one or more lowercase letters 2. one or more uppercase letters. 3. one ore more numbers 4. one or more symbols 5. should be between 8 and 32 charachters")
    public String password;

}
