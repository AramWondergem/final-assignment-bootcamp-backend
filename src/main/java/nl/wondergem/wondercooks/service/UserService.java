package nl.wondergem.wondercooks.service;


import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.PasswordRequest;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.mapper.UserMapper;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.RoleRepository;
import nl.wondergem.wondercooks.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repos;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, AuthenticationManager authManager, AuthService authService, RoleRepository roleRepository){
        this.repos = userRepository;
        this.userMapper = userMapper;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;

        this.roleRepository = roleRepository;
    }

    public String saveUser(UserInputDto userInputDto) {

        if(!repos.existsById(userInputDto.username)) {

            User newUser = userMapper.userInputDtoToUser(userInputDto);

            return repos.save(newUser).getUsername();
        }
        else {
            throw new BadRequestException("Username already used");
        }

    }

    public UserDto getUser(String id){

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

    public void updatePassword(UserDetails ud,PasswordRequest passwordRequest) {


        Authentication auth = authService.authenticationChecker(ud.getUsername(),passwordRequest.oldPassword);

        UserDetails uds = (UserDetails) auth.getPrincipal();

        User user = repos.getReferenceById(uds.getUsername());

        user.setPassword(passwordEncoder.encode(passwordRequest.newPassword));

        repos.save(user);
    }

    public void updateRoleWithCook(UserDetails ud){


        User user = repos.getReferenceById(ud.getUsername());

        user.addRole(roleRepository.getReferenceById("COOK"));

        repos.save(user);

    }

    // todo add changeRoles

    public void deleteUser(String user){
        repos.deleteById(user);
    }
}
