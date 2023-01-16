package nl.wondergem.wondercooks.controller;

import nl.wondergem.wondercooks.dto.AuthDto;
import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.model.Role;
import nl.wondergem.wondercooks.security.JwtRequestFilter;
import nl.wondergem.wondercooks.security.JwtService;
import nl.wondergem.wondercooks.service.AuthService;
import nl.wondergem.wondercooks.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;



    AuthDto authDto;
    UserDto userDto;
    String token;


    @BeforeEach
    void setUp() {

        authDto = new AuthDto();

        authDto.email = "user@test.nl";
        authDto.password = "Hallo1test!";

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        userDto = new UserDto();
        userDto.username = "user";
        userDto.email = "user@test.nl";
        userDto.roles = roles;

        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY3Mzc3NjU2NCwiaWF0IjoxNjcyOTEyNTY0fQ._v3eDIvgAvvQXeh_tDEHQz0QKjW4wM2hfCjCDXUysd8";

    }


    @Test
    void signIn() throws Exception {
        // arrange
        given(authService.signIn(authDto)).willReturn(token);
        given(userService.getUser("user@test.nl")).willReturn(userDto);

        String username = authDto.email;
        String password = authDto.password;


        String body = "{ \"email\":\" " + username + "\", \"password\":\" "
                + password + "\"}";

        // act and assert
        mockMvc.perform(post("/v1/auth").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}