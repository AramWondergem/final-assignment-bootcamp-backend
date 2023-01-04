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

    public String signIn(AuthDto authDto) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(authDto.username, authDto.password);


        Authentication auth = authManager.authenticate(up);

        UserDetails ud = (UserDetails) auth.getPrincipal();

        return jwtService.generateToken(ud);

    }
}
