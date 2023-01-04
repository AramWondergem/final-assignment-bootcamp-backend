package nl.wondergem.wondercooks.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserInputDto {
    @NotBlank
    public String username;
    @NotBlank
    public String password;
    @NotNull
    public String[] roles;
}
