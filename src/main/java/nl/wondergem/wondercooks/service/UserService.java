package nl.wondergem.wondercooks.service;


import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.PasswordRequest;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.dto.inputDto.UserUpdateDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.mapper.UserMapper;
import nl.wondergem.wondercooks.model.Role;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
// todo misschien verwijderen

//    public UserDto getUser(long id){
//
//        User user = repos.getReferenceById(id);
//        return userMapper.userToUserDto(user);
//    }

    public UserDto getUser(String email){

        User user = repos.findByEmail(email).get();
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

    public UserDto updateUser(UserUpdateDto userUpdateDto, String email) {

        User user = repos.findByEmail(email).get();
        User updatedUser = userMapper.userUpdateDtoToUser(userUpdateDto,user);
        updatedUser = repos.save(updatedUser);

        return userMapper.userToUserDto(updatedUser);

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

    public UserDto updateRoleWithCook(String email ){


        Optional<User> userOptional = repos.findByEmail(email);

        if(userOptional.isPresent()){

            User user = userOptional.get();

            user.addRole(Role.COOK);

            User changedUser = repos.save(user);

            return userMapper.userToUserDto(changedUser);

        } else {
            throw new UsernameNotFoundException(email);
        }



    }

    // todo add changeRoles

    public boolean deleteUser(String email){

       return repos.deleteByEmail(email);
    }
}
