package nl.wondergem.wondercooks.security;


import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepos;

    public MyUserDetailsService(UserRepository repos) {
        this.userRepos = repos;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> ou = userRepos.findByEmail(email);
        if (ou.isPresent()) {
            User user = ou.get();
            return new MyUserDetails(user);
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
