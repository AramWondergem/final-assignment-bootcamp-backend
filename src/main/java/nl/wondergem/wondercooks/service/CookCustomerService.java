package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.UserDto;
import nl.wondergem.wondercooks.dto.inputDto.UserInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.model.CookCustomer;
import nl.wondergem.wondercooks.model.Role;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.CookCustomerRepository;
import nl.wondergem.wondercooks.repository.UserRepository;
import nl.wondergem.wondercooks.security.MyUserDetails;
import org.springframework.stereotype.Service;

@Service
public class CookCustomerService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final CookCustomerRepository cookCustomerRepository;


    public CookCustomerService(UserRepository userRepository, UserService userService, CookCustomerRepository cookCustomerRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.cookCustomerRepository = cookCustomerRepository;
    }

    public CookCustomer createRelationCook(MyUserDetails ud, UserInputDto userInputDto) {

            User cook = ud.getUser();
            User customer;

            if (userRepository.existsByEmail(userInputDto.email)) {
                customer = userRepository.findByEmail(userInputDto.email).get();
            } else {
                UserDto userDto = userService.saveUser(userInputDto);
                customer = userRepository.getReferenceById(userDto.id);
            }

            CookCustomer cookCustomer = new CookCustomer();
            cookCustomer.setCook(cook);
            cookCustomer.setCustomer(customer);

            return cookCustomerRepository.save(cookCustomer);

    }

    public void createRelationCustomer(MyUserDetails ud, long id) {

        User cook = userRepository.getReferenceById(id);
        User customer= ud.getUser();

        CookCustomer cookCustomer = new CookCustomer();
        cookCustomer.setCook(cook);
        cookCustomer.setCustomer(customer);

        cookCustomerRepository.save(cookCustomer);

    }
}
