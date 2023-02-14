package nl.wondergem.wondercooks.dto;


import javax.validation.constraints.NotBlank;

public class AuthDto {
    @NotBlank
    public String email;

    @NotBlank
    public String password;
}
