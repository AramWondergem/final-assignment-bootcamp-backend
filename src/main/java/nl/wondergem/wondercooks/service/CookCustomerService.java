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

import java.util.Set;

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

        Set<CookCustomer> customers = cook.getCookCustomerCookSide();


        boolean cookCustomerRelationExists = false;
        if (customers != null) {
            for (CookCustomer cookCustomer : customers) {
                if (cookCustomer.getCustomer().getId() == customer.getId()) {
                    cookCustomerRelationExists = true;
                    break;
                }
            }
        }

        if (!cookCustomerRelationExists) {

            CookCustomer cookCustomer = new CookCustomer();
            cookCustomer.setCook(cook);
            cookCustomer.setCustomer(customer);

            return cookCustomerRepository.save(cookCustomer);
        } else {
            throw new BadRequestException("The relation already exists between cook and customer");
        }


    }

    public void createRelationCustomer(MyUserDetails ud, long id) {

        User cook = userRepository.getReferenceById(id);
        User customer = ud.getUser();

        Set<CookCustomer> cooks = customer.getCookCustomerCustomerSide();

        boolean cookCustomerRelationExists = false;

        if (cooks != null) {

            for (CookCustomer cookCustomer :
                    cooks) {
                if (cookCustomer.getCook().getId() == id) {
                    cookCustomerRelationExists = true;
                    break;
                }
            }
        }
        if (!cookCustomerRelationExists) {

            CookCustomer cookCustomer = new CookCustomer();
            cookCustomer.setCook(cook);
            cookCustomer.setCustomer(customer);

            cookCustomerRepository.save(cookCustomer);
        } else {
            throw new BadRequestException("The relation already exists between cook and customer");
        }

    }

    public void deleteCookCustomer(long id){
        cookCustomerRepository.deleteById(id);
    }
}
