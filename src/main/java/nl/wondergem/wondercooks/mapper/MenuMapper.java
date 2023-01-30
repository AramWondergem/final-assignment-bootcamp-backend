package nl.wondergem.wondercooks.mapper;

import nl.wondergem.wondercooks.dto.MenuDto;
import nl.wondergem.wondercooks.dto.MenuDtoSmall;
import nl.wondergem.wondercooks.dto.UserDtoSmall;
import nl.wondergem.wondercooks.dto.inputDto.MenuDtoInput;
import nl.wondergem.wondercooks.model.Menu;
import nl.wondergem.wondercooks.model.MenuType;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.service.UserService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MenuMapper {

    private final UserMapper userMapper;
    private final UserService userService;

    public MenuMapper(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }


    public MenuDto menuToMenuDto(Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.id = menu.getId();
        menuDto.cook = userMapper.userToUserDtoSmall(menu.getCook());

        Set<UserDtoSmall> customers = new HashSet<>();
        if(menu.getCustomers() != null) {
            for (User customer:
                 menu.getCustomers()) {

                UserDtoSmall customerDto = userMapper.userToUserDtoSmall(customer);

                customers.add(customerDto);

            }
        }
        menuDto.customers = customers;
        menuDto.title = menu.getTitle();
        menuDto.starter = menu.getStarter();
        menuDto.main = menu.getMain();
        menuDto.side = menu.getSide();
        menuDto.dessert = menu.getDessert();
        menuDto.menuDescription = menu.getMenuDescription();
        menuDto.menuPictureURL = menu.getMenuPictureURL();
        menuDto.menuType = menu.getMenuType();
        menuDto.warmUpInstruction = menu.getWarmUpInstruction();
        menuDto.orderDeadline = menu.getOrderDeadline();
        menuDto.startDeliveryWindow = menu.getStartDeliveryWindow();
        menuDto.endDeliveryWindow = menu.getEndDeliveryWindow();
        menuDto.numberOfMenus = menu.getNumberOfMenus();
        menuDto.priceMenu = menu.getPriceMenu();
        menuDto.tikkieLink = menu.getTikkieLink();

        return menuDto;

    }

    public MenuDtoSmall menuToMenuDtoSmall(Menu menu){
        MenuDtoSmall menuDtoSmall = new MenuDtoSmall();

        menuDtoSmall.id = menu.getId();
        menuDtoSmall.title = menu.getTitle();
        menuDtoSmall.starter = menu.getStarter();
        menuDtoSmall.main = menu.getMain();
        menuDtoSmall.side = menu.getSide();
        menuDtoSmall.dessert = menu.getDessert();
        menuDtoSmall.menuDescription = menu.getMenuDescription();
        menuDtoSmall.menuPictureURL = menu.getMenuPictureURL();
        menuDtoSmall.menuType = menu.getMenuType();
        menuDtoSmall.warmUpInstruction = menu.getWarmUpInstruction();
        menuDtoSmall.orderDeadline = menu.getOrderDeadline();
        menuDtoSmall.startDeliveryWindow = menu.getStartDeliveryWindow();
        menuDtoSmall.endDeliveryWindow = menu.getEndDeliveryWindow();
        menuDtoSmall.numberOfMenus = menu.getNumberOfMenus();
        menuDtoSmall.priceMenu = menu.getPriceMenu();
        menuDtoSmall.tikkieLink = menu.getTikkieLink();

        return menuDtoSmall;


    }

    public Menu menuInputDtoToMenu(MenuDtoInput menuDtoInput){

        Menu menu = new Menu();

        menu.setCook(userService.getUser(menuDtoInput.cookId));

        Set<User> customers = new HashSet<>();
        if(menuDtoInput.customersId != null) {
            for (int id :
                    menuDtoInput.customersId) {

                User customer = userService.getUser((long) id);
                customers.add(customer);

            }
        }

        menu.setCustomers(customers);
        menu.setTitle(menuDtoInput.title);
        menu.setStarter(menuDtoInput.starter);
        menu.setMain(menuDtoInput.main);
        menu.setSide(menuDtoInput.side);
        menu.setDessert(menuDtoInput.dessert);
        menu.setMenuDescription(menuDtoInput.menuDescription);
        menu.setMenuPictureURL(menuDtoInput.menuPictureURL);
        menu.setMenuType(MenuType.valueOf(menuDtoInput.menuType));
        menu.setWarmUpInstruction(menuDtoInput.warmUpInstruction);
        menu.setOrderDeadline(menuDtoInput.orderDeadline);
        menu.setStartDeliveryWindow(menuDtoInput.startDeliveryWindow);
        menu.setEndDeliveryWindow(menuDtoInput.endDeliveryWindow);
        menu.setNumberOfMenus(menuDtoInput.numberOfMenus);
        menu.setPriceMenu(menuDtoInput.priceMenu);
        menu.setTikkieLink(menuDtoInput.tikkieLink);

        return menu;



    }
}
