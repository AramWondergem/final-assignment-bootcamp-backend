package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.MenuDto;
import nl.wondergem.wondercooks.dto.inputDto.MenuInputDto;
import nl.wondergem.wondercooks.mapper.MenuMapper;
import nl.wondergem.wondercooks.model.EmailDetails;
import nl.wondergem.wondercooks.model.Menu;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    private final EmailServiceImpl emailService;

    public MenuService(MenuRepository menuRepository, MenuMapper menuMapper, EmailServiceImpl emailService) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
        this.emailService = emailService;
    }

    public MenuDto saveMenu(MenuInputDto menuInputDto) {

        Menu emptyMenu = new Menu();

        Menu menu = menuMapper.menuInputDtoToMenu(menuInputDto, emptyMenu);

        Menu menuReturned = menuRepository.save(menu);

        return menuMapper.menuToMenuDto(menuReturned);
    }

    public MenuDto getMenu(long id) {
        return menuMapper.menuToMenuDto(menuRepository.getReferenceById(id));
    }

    public void updateMenu(MenuInputDto menuInputDto,long id){

        Menu menuToBeUpdated = menuRepository.getReferenceById(id);

        Menu updatedMenu = menuMapper.menuInputDtoToMenu(menuInputDto, menuToBeUpdated);

        menuRepository.save(updatedMenu);

    }

    public void deleteMenu(long id){
        menuRepository.deleteById(id);
    }

    public void sendMenu(long id){

        Menu menuToBeUpdated = menuRepository.getReferenceById(id);

        menuToBeUpdated.setSendToCustomers(true);

        Set<User> customers = menuToBeUpdated.getCustomers();

        for (User customer:
             customers) {
            EmailDetails emailDetails = new EmailDetails("wonderreclame@gmail.com", "New Menu",
                    "Hi " + customer.getUsername() + "," +
                            "\n\nCheckout your Wonder Gems account, because there is a new menu for you. It is a menu of your favorite cook " +
                    menuToBeUpdated.getCook().getUsername() + "." +
                    "\n\nKind regards,\n\n The Elves from the backend");
            emailService.sendSimpleMail(emailDetails);
        }

        menuRepository.save(menuToBeUpdated);
    }

}
