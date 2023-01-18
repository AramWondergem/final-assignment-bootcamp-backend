package nl.wondergem.wondercooks.service;


import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.PasswordRequest;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.mapper.UserMapper;
import nl.wondergem.wondercooks.model.Role;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repos;
    private final UserMapper userMapper;
//    private final AuthService authService;
//
//    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, AuthenticationManager authManager, AuthService authService){
        this.repos = userRepository;
        this.userMapper = userMapper;
//        this.authService = authService;
//        this.passwordEncoder = passwordEncoder;

    }

    public UserDto saveUser(UserInputDto userInputDto) {

        if(!repos.existsByEmail(userInputDto.email)) {

            Set<Role> roles = new HashSet<>();
            roles.add(Role.USER);

            User newUser = userMapper.userInputDtoToUser(userInputDto,roles);

            return userMapper.userToUserDto(repos.save(newUser));
        }
        else {
            throw new BadRequestException("Username already used");
        }

    }

    public UserDto getUser(long id){

        User user = repos.getReferenceById(id);
        return userMapper.userToUserDto(user);
    }

    public Iterable<UserDto> getAllUsers(){
        List<User> reposUserList = repos.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user :
                reposUserList) {

            userDtos.add(userMapper.userToUserDto(user));

        }
        return userDtos;
    }
//todo mischien weghalen
//    public User updateUser(String username, UserInputDto userInputDto){
//
//        if(repos.existsById(username) && username.equals(userInputDto.getUsername())) {
//
//            User userToBeUpdated = repos.getReferenceById(username);
//
//
//
//                    userMapper.userInputDtoToUser(userInputDto);
//
//            return repos.save(newUser).getUsername();
//            return repos.getReferenceById(saveUser(userInputDto));
//        } else {
//            throw new UsernameNotFoundException("Username does not exist");
//        }
//
//    }

//    public void updatePassword(long id ,PasswordRequest passwordRequest) {
//
//
//        Authentication auth = authService.authenticationChecker(ud.getUsername(),passwordRequest.oldPassword);
//
//        UserDetails uds = (UserDetails) auth.getPrincipal();
//
//        User user = repos.getReferenceById(id);
//
//        user.setPassword(passwordEncoder.encode(passwordRequest.newPassword));
//
//        repos.save(user);
//    }

    public void updateRoleWithCook(long id ){


        User user = repos.getReferenceById(id);

        user.addRole(Role.COOK);

        repos.save(user);

    }

    // todo add changeRoles

    public void deleteUser(long id){
        repos.deleteById(id);
    }
}
