package nl.wondergem.wondercooks.mapper;


import nl.wondergem.wondercooks.dto.*;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.dto.inputDto.UserUpdateDto;
import nl.wondergem.wondercooks.model.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final MenuMapper menuMapper;
    private final OrderMapper orderMapper;


    public UserMapper(PasswordEncoder passwordEncoder, MenuMapper menuMapper, OrderMapper orderMapper) {
        this.passwordEncoder = passwordEncoder;

        this.menuMapper = menuMapper;

        this.orderMapper = orderMapper;
    }


    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setStreetAndNumber(user.getStreetAndNumber());
        userDto.setZipcode(user.getZipcode());
        userDto.setCity(user.getCity());
        userDto.setFavoriteColour(user.getFavoriteColour());
        userDto.setAllergies(user.getAllergies());
        userDto.setAllergiesExplanation(user.getAllergiesExplanation());
        userDto.setProfilePicture(user.getProfilePicture());

        Set<UserDtoSmall> cooks = new HashSet<>();

        if (user.getCookCustomerCustomerSide() != null) {

            for (CookCustomer cookCustomer :
                    user.getCookCustomerCustomerSide()) {

                UserDtoSmall cook = userToUserDtoSmall(cookCustomer.getCook());
                cooks.add(cook);

            }
        }


        Set<UserDtoSmall> customers = new HashSet<>();

        if (user.getCookCustomerCookSide() != null) {
            for (CookCustomer cookCustomer :
                    user.getCookCustomerCookSide()) {

                UserDtoSmall customer = userToUserDtoSmall(cookCustomer.getCustomer());
                customers.add(customer);

            }
        }

        Set<MenuDto> menusAsCook = new HashSet<>();

        if (user.getMenusAsCook() != null) {
            for (Menu menu : user.getMenusAsCook()) {
                MenuDto menuDtoSmall = menuMapper.menuToMenuDto(menu);
                menusAsCook.add(menuDtoSmall);
            }
        }

        Set<MenuDto> menusAsCustomer = new HashSet<>();

        if (user.getMenusAsCustomer() != null) {
            for (Menu menu : user.getMenusAsCustomer()) {
                MenuDto menuDto = menuMapper.menuToMenuDto(menu);
                menusAsCustomer.add(menuDto);
            }
        }

        Set<OrderDtoSmall> orders = new HashSet<>();

        if (user.getOrders() != null) {
            for (Order order : user.getOrders()) {
                OrderDtoSmall orderDto = orderMapper.orderToOrderDtoSmall(order);
                orders.add(orderDto);
            }
        }


        userDto.setCooks(cooks);
        userDto.setCustomers(customers);
        userDto.setMenusAsCook(menusAsCook);
        userDto.setMenusAsCustomers(menusAsCustomer);
        userDto.setOrders(orders);


        return userDto;
    }

    public UserDtoSmall userToUserDtoSmall(User user) {
        UserDtoSmall userDtoSmall = new UserDtoSmall();
        userDtoSmall.setEmail(user.getEmail());
        userDtoSmall.setRoles(user.getRoles());
        userDtoSmall.setUsername(user.getUsername());
        userDtoSmall.setId(user.getId());
        userDtoSmall.setStreetAndNumber(user.getStreetAndNumber());
        userDtoSmall.setZipcode(user.getZipcode());
        userDtoSmall.setCity(user.getCity());
        userDtoSmall.setFavoriteColour(user.getFavoriteColour());
        userDtoSmall.setAllergies(user.getAllergies());
        userDtoSmall.setAllergiesExplanation(user.getAllergiesExplanation());
        userDtoSmall.setProfilePicture(user.getProfilePicture());

        return userDtoSmall;
    }

    public User userInputDtoToUser(UserInputDto userInputDto, Set<Role> roles) {

        User user = new User();

        user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        user.setEmail(userInputDto.getEmail());
        user.setUsername(userInputDto.getUsername());
        user.setRoles(roles);

        return user;


    }

    public User userUpdateDtoToUser(UserUpdateDto userUpdateDto, User user) {

        user.setEmail(userUpdateDto.email);
        user.setUsername(userUpdateDto.username);
        user.setStreetAndNumber(userUpdateDto.streetAndNumber);
        user.setZipcode(userUpdateDto.zipcode);
        user.setCity(userUpdateDto.city);
        user.setFavoriteColour(userUpdateDto.favoriteColour);
        user.setAllergies(userUpdateDto.allergies);
        user.setAllergiesExplanation(userUpdateDto.allergiesExplanation);
        user.setProfilePicture(userUpdateDto.profilePicture);

        return user;

    }

}


