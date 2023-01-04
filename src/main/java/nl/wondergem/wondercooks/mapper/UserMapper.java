package nl.wondergem.wondercooks.mapper;


import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.UserInputDto;
import nl.wondergem.wondercooks.model.Role;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.service.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserMapper {
private final PasswordEncoder passwordEncoder;
private final RoleService roleService;

    public UserMapper(PasswordEncoder passwordEncoder, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

   public User userInputDtoToUser(UserInputDto userInputDto){

        User user = new User();

        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        user.setUsername(userInputDto.getUsername());

        Collection<Role> roles = new ArrayList<>();

        for (String s :
                userInputDto.roles) {
            roles.add(roleService.getRole(s));

        }
        user.setRoles(roles);

        return user;


    }

}


