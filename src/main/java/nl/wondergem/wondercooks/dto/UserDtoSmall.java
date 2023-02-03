package nl.wondergem.wondercooks.dto;

import lombok.Getter;
import lombok.Setter;
import nl.wondergem.wondercooks.model.Role;

import java.util.Collection;

@Getter
@Setter
public class UserDtoSmall {
    public long id;
    public String email;
    public String username;
    public Collection<Role> roles;
    public String streetAndNumber;
    public String zipcode;
    public String city;
    public String favoriteColour;
    public String allergies;
    public String allergiesExplanation;
    public String profilePicture;

}
