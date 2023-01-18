package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.mapper.UserMapper;
import nl.wondergem.wondercooks.model.Role;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repos;
    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    String email;

    @BeforeEach
    void setup() {

        email = "test@test.nl";

    }

    @Test
    @Disabled
    void saveUser() {
        // Arrange
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.password = "test";
        userInputDto.email = "test@test.nl";
        userInputDto.username ="testpersoon";

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);


        User newUser = new User();
        newUser.setUsername("testpersoon");
        newUser.setPassword("test");
        newUser.setId(1);
        newUser.setEmail("test@test.nl");
        newUser.setRoles(roles);

        UserDto userDto = new UserDto();
        userDto.id = 1;
        userDto.email = "test@test.nl";
        userDto.username = "testpersoon";
        userDto.roles = roles;

        when(repos.existsByEmail(userInputDto.email)).thenReturn(false);
        when(userMapper.userInputDtoToUser(userInputDto,roles)).thenReturn(newUser);
        when(repos.save(newUser)).thenReturn(newUser);
        when(userMapper.userToUserDto(newUser)).thenReturn(userDto);

        //act
        UserDto result = userService.saveUser(userInputDto);

        //assert
        assertEquals(1, result.id);
        assertEquals("test@test.nl", result.email);
        assertEquals("testpersoon", result.username);
        assertTrue(result.roles.contains(Role.USER));



    }

    @Test
    @Disabled
    void getUser() {
    }

    @Test
    @Disabled
    void testGetUser() {
    }

    @Test
    @Disabled
    void getAllUsers() {
    }

    @Test
    @Disabled
    void updateRoleWithCook() {
    }

    @Test
    @Disabled
    void deleteUser() {
    }
}