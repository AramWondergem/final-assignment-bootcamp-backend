package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.model.CookCustomer;
import nl.wondergem.wondercooks.model.Role;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.CookCustomerRepository;
import nl.wondergem.wondercooks.repository.UserRepository;
import nl.wondergem.wondercooks.security.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CookCustomerServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserService userService;
    @Mock
    CookCustomerRepository cookCustomerRepository;

    @InjectMocks
    CookCustomerService cookCustomerService;

    UserInputDto userInputDto;

    MyUserDetails myUserDetails;

    UserDto customerUserDto;

    User customer;
    User cook;

    CookCustomer cookCustomer;


    @BeforeEach
    public void setup() {
        userInputDto = new UserInputDto();
        userInputDto.password = "test";
        userInputDto.email = "test@test.nl";
        userInputDto.username = "testpersoon";

        Set<Role> rolesCook = new HashSet<>();
        rolesCook.add(Role.USER);
        rolesCook.add(Role.COOK);
        rolesCook.add(Role.ADMIN);

        cook = new User();
        cook.setUsername("cook");
        cook.setPassword("test");
        cook.setId(1);
        cook.setEmail("cook@test.nl");
        cook.setRoles(rolesCook);

        myUserDetails = new MyUserDetails(cook);

        Set<Role> rolesCustomer = new HashSet<>();
        rolesCustomer.add(Role.USER);


        customer = new User();
        customer.setUsername("customer");
        customer.setPassword("test");
        customer.setId(2);
        customer.setEmail("customer@test.nl");
        customer.setRoles(rolesCustomer);

        customerUserDto = new UserDto();
        customerUserDto.id = 2;
        customerUserDto.email = "customer@test.nl";
        customerUserDto.username = "customer";
        customerUserDto.roles = rolesCustomer;

        cookCustomer = new CookCustomer();
        cookCustomer.setCustomer(customer);
        cookCustomer.setCook(cook);
        cookCustomer.setId(1);

    }


    @Test
    @DisplayName("WhenCookWantToMakeARelationWithNewUserThenTheUserSerViceSavemethodIsCalled")
    void createRelationCookWithNewUser() {
        //arrange
        when(userRepository.existsByEmail(userInputDto.email)).thenReturn(false);
        when(userService.saveUser(userInputDto)).thenReturn(customerUserDto);
        when(userRepository.getReferenceById(customerUserDto.id)).thenReturn(customer);
        when(cookCustomerRepository.save(any())).thenReturn(cookCustomer);

        //act

        CookCustomer result = cookCustomerService.createRelationCook(myUserDetails,userInputDto);

        //assert

        verify(userRepository).existsByEmail(userInputDto.email);
        verify(userService).saveUser(userInputDto);
        verify(userRepository).getReferenceById(customerUserDto.id);
        verify(cookCustomerRepository).save(any());
        assertEquals("cook", result.getCook().getUsername());
        assertEquals("customer", result.getCustomer().getUsername());
    }

    @Test
    @DisplayName("WhenCookWantToConnectWithKnownUsesThenTheCustomerIsReturnedByTheUserRepository")
    void createRelationCookWithKnownUser() {
        when(userRepository.existsByEmail(userInputDto.email)).thenReturn(true);
        when(userRepository.findByEmail(userInputDto.email)).thenReturn(Optional.of(customer));
        when(cookCustomerRepository.save(any())).thenReturn(cookCustomer);

        //act

        CookCustomer result = cookCustomerService.createRelationCook(myUserDetails,userInputDto);

        //assert

        verify(userRepository).existsByEmail(userInputDto.email);
        verify(userRepository).findByEmail(userInputDto.email);
        verify(cookCustomerRepository).save(any());
        assertEquals("cook", result.getCook().getUsername());
        assertEquals("customer", result.getCustomer().getUsername());
    }

    @Test
    @DisplayName("WhenCustomerWantToBeCustomerOfCookThenTheMethodsAreCalledInTheFunction")
    void createRelationCustomer(){
        //Arrange
        long id = 2;
        MyUserDetails myUserDetails1 = new MyUserDetails(customer);


        when(userRepository.getReferenceById(id)).thenReturn(cook);
        when(cookCustomerRepository.save(any())).thenReturn(cookCustomer);

        //Act

        cookCustomerService.createRelationCustomer(myUserDetails1, id);

        //Assert

        verify(userRepository).getReferenceById(id);
        verify(cookCustomerRepository).save(any());
    }
}