package nl.wondergem.wondercooks.mapper;


import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
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

}


