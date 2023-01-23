package nl.wondergem.wondercooks.mapper;


import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.dto.inputDto.UserUpdateDto;
import nl.wondergem.wondercooks.model.Role;
import nl.wondergem.wondercooks.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserMapper {
private final PasswordEncoder passwordEncoder;


    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

    }


    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setStreetAndNumber(user.getStreetAndNumber());
        userDto.setZipcode(user.getZipcode());
        userDto.setCity(user.getCity());
        userDto.setFavoriteColour(user.getFavoriteColour());
        userDto.setAllergies(user.getAllergies());
        userDto.setAllergiesExplanation(user.getAllergiesExplanation());
        userDto.setProfilePicture(user.getProfilePicture());

        return userDto;
    }

   public User userInputDtoToUser(UserInputDto userInputDto, Set<Role> roles){

        User user = new User();

        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        user.setEmail(userInputDto.getEmail());
        user.setUsername(userInputDto.getUsername());
        user.setRoles(roles);

        return user;


    }

    public User userUpdateDtoToUser(UserUpdateDto userUpdateDto, User user) {

        user.setEmail(userUpdateDto.email);
        user.setUsername(userUpdateDto.username);
        user.setStreetAndNumber(userUpdateDto.streetAndNumber);
        user.setZipcode(userUpdateDto.zipcode);
        user.setCity(userUpdateDto.city);
        user.setFavoriteColour(userUpdateDto.favoriteColour);
        user.setAllergies(userUpdateDto.allergies);
        user.setAllergiesExplanation(userUpdateDto.allergiesExplanation);
        user.setProfilePicture(userUpdateDto.profilePicture);

        return user;

    }

}


