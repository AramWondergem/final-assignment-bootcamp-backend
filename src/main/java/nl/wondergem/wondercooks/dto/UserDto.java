package nl.wondergem.wondercooks.dto;

import lombok.Getter;
import lombok.Setter;
import nl.wondergem.wondercooks.model.Role;

import java.util.Collection;

@Getter
@Setter
public class UserDto {
    public String username;
    public Collection<Role> roles;
}
