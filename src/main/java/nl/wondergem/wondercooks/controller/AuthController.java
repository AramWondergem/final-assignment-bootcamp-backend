package nl.wondergem.wondercooks.controller;


import nl.wondergem.wondercooks.dto.AuthDto;
import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.service.AuthService;
import nl.wondergem.wondercooks.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${apiPrefix}/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<Object> signIn(@RequestBody AuthDto authDto) {

        try {

            String token = authService.signIn(authDto);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated");


        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
