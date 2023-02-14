package nl.wondergem.wondercooks.dto;

import lombok.Getter;
import lombok.Setter;
import nl.wondergem.wondercooks.model.Role;

import java.util.Collection;

@Getter
@Setter
public class UserDto {
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
    public Collection<UserDtoSmall> customers;
    public Collection<UserDtoSmall> cooks;
    public Collection<MenuDto> menusAsCook;
    public Collection<MenuDto> menusAsCustomers;
    public Collection<OrderDtoSmall> orders;
}
