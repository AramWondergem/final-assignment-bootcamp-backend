package nl.wondergem.wondercooks.service;


import nl.wondergem.wondercooks.dto.AuthDto;
import nl.wondergem.wondercooks.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final AuthenticationManager authManager;
    private final JwtService jwtService;


    public AuthService(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public Authentication authenticationChecker(String username, String password){
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(username, password);


        return authManager.authenticate(up);
    }

    public String signIn(AuthDto authDto) {

       Authentication auth = authenticationChecker(authDto.username,authDto.password);


        UserDetails ud = (UserDetails) auth.getPrincipal();

        return jwtService.generateToken(ud);

    }
}
