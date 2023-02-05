package nl.wondergem.wondercooks.service;


import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.dto.inputDto.UserUpdateDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.mapper.UserMapper;
import nl.wondergem.wondercooks.model.*;
import nl.wondergem.wondercooks.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository repos;
    private final UserMapper userMapper;
    private final CookCustomerService cookCustomerService;
    private final MenuService menuService;

    private final OrderService orderService;


    public UserService(UserRepository userRepository, @Lazy UserMapper userMapper, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, AuthenticationManager authManager, AuthService authService, @Lazy CookCustomerService cookCustomerService, @Lazy MenuService menuService, @Lazy OrderService orderService){
        this.repos = userRepository;
        this.userMapper = userMapper;

        this.cookCustomerService = cookCustomerService;
        this.menuService = menuService;
        this.orderService = orderService;
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

    public User getUser(long id){
        return repos.getReferenceById(id);
    }

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

    public void deleteUser(String email){

        User user = repos.findByEmail(email).get();

        if(user.getCookCustomerCookSide().size()>0){

            for (CookCustomer relation :
                    user.getCookCustomerCookSide()) {

                cookCustomerService.deleteCookCustomer(relation.getId());
            }


        }

        if(user.getCookCustomerCustomerSide().size()>0){

            for (CookCustomer relation :
                    user.getCookCustomerCustomerSide()) {

                cookCustomerService.deleteCookCustomer(relation.getId());
            }


        }

        if(user.getMenusAsCook().size()>0){

            for (Menu menu :
                    user.getMenusAsCook()) {

                menuService.deleteMenu(menu.getId());
            }


        }

        if(user.getMenusAsCustomer().size()>0) {

            for(Menu menu: user.getMenusAsCustomer()) {
                menuService.removeCustomer(user,menu.getId());
            }
        }

        if(user.getOrders().size()>0){
            for(Order order: user.getOrders()) {
                orderService.deleteOrder(order.getId());
            }
        }



       repos.deleteById(user.getId());
    }
}
