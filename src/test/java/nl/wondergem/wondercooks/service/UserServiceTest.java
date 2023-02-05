package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.dto.inputDto.UserUpdateDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.mapper.UserMapper;
import nl.wondergem.wondercooks.model.Role;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repos;
    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    String email;
    UserInputDto userInputDto;
    UserDto userDto;
    UserDto userDto2;
    Set<Role> roles;
    User newUser;
    User newUser2;

    @BeforeEach
    void setup() {

        email = "test@test.nl";

        userInputDto = new UserInputDto();
        userInputDto.password = "test";
        userInputDto.email = "test@test.nl";
        userInputDto.username = "testpersoon";

        roles = new HashSet<>();
        roles.add(Role.USER);

        userDto = new UserDto();
        userDto.id = 1;
        userDto.email = "test@test.nl";
        userDto.username = "testpersoon";
        userDto.roles = roles;

        userDto2 = new UserDto();
        userDto2.id = 2;
        userDto2.email = "test2@test.nl";
        userDto2.username = "testpersoon2";
        userDto2.roles = roles;

        newUser = new User();
        newUser.setUsername("testpersoon");
        newUser.setPassword("test");
        newUser.setId(1);
        newUser.setEmail("test@test.nl");
        newUser.setRoles(roles);

        newUser2 = new User();
        newUser2.setUsername("testpersoon2");
        newUser2.setPassword("test2");
        newUser2.setId(2);
        newUser2.setEmail("test2@test.nl");
        newUser2.setRoles(roles);


    }

    @Test
    @DisplayName("test for saving user")
    void saveUser() {
        // Arrange

        when(repos.existsByEmail(userInputDto.email)).thenReturn(false);
        when(userMapper.userInputDtoToUser(userInputDto, roles)).thenReturn(newUser);
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
    @DisplayName("WhenUsernameExistThensaveUserFunctionShouldTrowException")
    void saveUserThrowException() {
        //arrange
        when(repos.existsByEmail(userInputDto.email)).thenReturn(true);
        //act & arrange
        assertThrows(BadRequestException.class, () -> userService.saveUser(userInputDto));
    }

    @Test
    @DisplayName("WhenUserExistThenGetUserShouldGiveBackUserDto")
    void getUser() {
        //Arrange
        when(repos.findByEmail(email)).thenReturn(Optional.of(newUser));
        when(userMapper.userToUserDto(newUser)).thenReturn(userDto);
        //Act
        UserDto result = userService.getUser(email);
        //Assert
        assertEquals(email, result.email);
        assertEquals("testpersoon", result.username);
        assertTrue(result.roles.contains(Role.USER));
        assertEquals(1, result.id);

    }


    @Test
   @DisplayName("WhenGetAllUsersIsCalledThenAllUsersAreReturnedInList")
    void getAllUsers() {
        //Arrange
        List<User> reposUserList = new ArrayList<>();
        reposUserList.add(newUser);
        reposUserList.add(newUser2);

        when(repos.findAll()).thenReturn(reposUserList);
        when(userMapper.userToUserDto(newUser)).thenReturn(userDto);
        when(userMapper.userToUserDto(newUser2)).thenReturn(userDto2);
        //Act
        List<UserDto> result = (List<UserDto>) userService.getAllUsers();
        //Assert
        assertEquals(2, result.size());
        assertEquals("test@test.nl", result.get(0).getEmail());
        assertEquals("test2@test.nl", result.get(1).getEmail());

    }

    @Test
    @DisplayName("WhenUserIsUpdatedWIthRoleAsCookThenAnUserDtoIsReturnedWithUpdatedValues")
    void updateRoleWithCook() {
        //arrange
        when(repos.findByEmail(email)).thenReturn(Optional.of(newUser));

        User changedUser = newUser;
        changedUser.addRole(Role.COOK);

        when(repos.save(changedUser)).thenReturn(changedUser);

        UserDto changedUserDto = userDto;

        HashSet<Role> roles2 = new HashSet<>();
        roles2.add(Role.COOK);
        roles2.add(Role.USER);
        changedUserDto.setRoles(roles2);
        when(userMapper.userToUserDto(changedUser)).thenReturn(changedUserDto);
        //act
        UserDto result = userService.updateRoleWithCook(email);
        //assert
        assertEquals(2, result.roles.size());
        assertTrue(result.roles.contains(Role.USER));
        assertTrue(result.roles.contains(Role.COOK));
        assertEquals(1, result.id);
        assertEquals("test@test.nl", result.email);
        assertEquals("testpersoon", result.username);

    }

    @Test
    @DisplayName("WhenUserIsDeletedThenTrueIsReturned")
    void deleteUser() {
        //arrange
        when(repos.deleteByEmail(email)).thenReturn(true);
        //act
        boolean result = userService.deleteUser(email);
        //Assert
        verify(repos).deleteByEmail(email);
        assertTrue(result);

    }

    @Test
    @DisplayName("WhenUserIsUpdatedWithUserUpdateDtoThenAMatchingUserDtoIsReturned")
    void updateUser() {
        //arrange
        UserUpdateDto userUpdateDto = new UserUpdateDto();

        userUpdateDto.setEmail("tijger@gmail.com");
        userUpdateDto.setUsername("tijger");
        userUpdateDto.setStreetAndNumber("kerkstraat 1");
        userUpdateDto.setZipcode("1234KL");
        userUpdateDto.setCity("Cattown");
        userUpdateDto.setFavoriteColour("Pink");
        userUpdateDto.setAllergies("salmon");
        userUpdateDto.setAllergiesExplanation("I will die");

        User updatedUser = newUser;
        updatedUser.setEmail("tijger@gmail.com");
        updatedUser.setUsername("tijger");
        updatedUser.setStreetAndNumber("kerkstraat 1");
        updatedUser.setZipcode("1234KL");
        updatedUser.setCity("Cattown");
        updatedUser.setFavoriteColour("Pink");
        updatedUser.setAllergies("salmon");
        updatedUser.setAllergiesExplanation("I will die");

        UserDto updatedUserDto = new UserDto();
        updatedUserDto.id = 1;
        updatedUserDto.roles = roles;
        updatedUserDto.email = "tijger@gmail.com";
        updatedUserDto.username = "tijger";
        updatedUserDto.streetAndNumber = "kerkstraat 1";
        updatedUserDto.zipcode = "1234KL";
        updatedUserDto.city = "Cattown";
        updatedUserDto.favoriteColour = "Pink";
        updatedUserDto.allergies = "salmon";
        updatedUserDto.allergiesExplanation = "I will die";

        when(repos.findByEmail(email)).thenReturn(Optional.of(newUser));
        when(userMapper.userUpdateDtoToUser(userUpdateDto,newUser)).thenReturn(updatedUser);
        when(repos.save(updatedUser)).thenReturn(updatedUser);
        when(userMapper.userToUserDto(updatedUser)).thenReturn(updatedUserDto);

        //act

        UserDto result = userService.updateUser(userUpdateDto,email);

        //assert

        verify(repos).findByEmail(email);
        verify(userMapper).userUpdateDtoToUser(userUpdateDto,newUser);
        verify(repos).save(updatedUser);
        verify(userMapper).userToUserDto(updatedUser);

        assertEquals(1, result.id);
        assertEquals(1, result.roles.size());
        assertTrue(result.roles.contains(Role.USER));
        assertEquals("tijger@gmail.com", result.email);
        assertEquals("tijger", result.username);
        assertEquals("kerkstraat 1", result.streetAndNumber);
        assertEquals("1234KL", result.zipcode);
        assertEquals("Cattown", result.city);
        assertEquals("Pink", result.favoriteColour);
        assertEquals("salmon", result.allergies);
        assertEquals("I will die", result.allergiesExplanation);
    }

    @Test
    void testGetUser() {
        //arrange
        when(repos.getReferenceById((long)1)).thenReturn(newUser);

        //act
        User result = userService.getUser(1);

        //assert
        verify(repos, times(1)).getReferenceById((long)1);
        assertEquals(newUser.getId(),result.getId());
        assertEquals(newUser.getUsername(),result.getUsername());
    }
}