package nl.wondergem.wondercooks.security;


import nl.wondergem.wondercooks.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService service, UserRepository userRepos) {
        this.jwtService = service;
        this.userRepository = userRepos;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().and()
                .authorizeRequests()
                // without token
                .antMatchers(HttpMethod.POST, "/v1/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
//                .antMatchers(HttpMethod.GET,"/v1/files/**").permitAll()
                // with token and role user
                .antMatchers("/v1/files/**").hasAuthority("USER")
                .antMatchers("/v1/users").hasAuthority("USER")
                .antMatchers("/v1/users/cook").hasAuthority("USER")
                .antMatchers("/v1/orders/**").hasAuthority("USER")
                .antMatchers(HttpMethod.POST, "/v1/cookcustomer/customer/**").hasAuthority("USER")
                .antMatchers(HttpMethod.GET, "/v1/menus/**").hasAuthority("USER")
                // with token and role cook
                .antMatchers("/v1/orders/decline/**").hasAuthority("COOK")
                .antMatchers("/v1/orders/accept/**").hasAuthority("COOK")
                .antMatchers("/v1/menus/**").hasAuthority("COOK")
                .antMatchers("/v1/deliveries/**").hasAuthority("COOK")
                .antMatchers(HttpMethod.POST, "/v1/cookcustomer/cook").hasAuthority("COOK")
                // with token and role admin
                .antMatchers(HttpMethod.GET, "/v1/users/all").hasAuthority("ADMIN")
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}