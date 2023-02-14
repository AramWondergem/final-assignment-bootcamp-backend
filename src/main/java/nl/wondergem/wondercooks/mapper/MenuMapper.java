package nl.wondergem.wondercooks.mapper;

import nl.wondergem.wondercooks.dto.MenuDto;
import nl.wondergem.wondercooks.dto.MenuDtoSmall;
import nl.wondergem.wondercooks.dto.OrderDtoSmall;
import nl.wondergem.wondercooks.dto.UserDtoSmall;
import nl.wondergem.wondercooks.dto.inputDto.MenuInputDto;
import nl.wondergem.wondercooks.model.Menu;
import nl.wondergem.wondercooks.model.MenuType;
import nl.wondergem.wondercooks.model.Order;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MenuMapper {

    private final UserMapper userMapper;
    private final UserService userService;

    private final OrderMapper orderMapper;

    public MenuMapper(@Lazy UserMapper userMapper, UserService userService, @Lazy OrderMapper orderMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.orderMapper = orderMapper;
    }


    public MenuDto menuToMenuDto(Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.id = menu.getId();
        menuDto.cook = userMapper.userToUserDtoSmall(menu.getCook());

        Set<UserDtoSmall> customers = new HashSet<>();
        if (menu.getCustomers() != null) {
            for (User customer :
                    menu.getCustomers()) {

                UserDtoSmall customerDto = userMapper.userToUserDtoSmall(customer);

                customers.add(customerDto);

            }
        }
        menuDto.customers = customers;

        Set<OrderDtoSmall> orders = new HashSet<>();

        if (menu.getOrders() != null) {
            for (Order order : menu.getOrders()) {
                OrderDtoSmall orderDtoSmall = orderMapper.orderToOrderDtoSmall(order);
                orders.add(orderDtoSmall);
            }
        }

        menuDto.orders = orders;

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
        menuDto.sendToCustomers = menu.isSendToCustomers();
        return menuDto;

    }

    public MenuDtoSmall menuToMenuDtoSmall(Menu menu) {
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
        menuDtoSmall.sendToCustomers = menu.isSendToCustomers();

        return menuDtoSmall;


    }

    public Menu menuInputDtoToMenu(MenuInputDto menuInputDto, Menu menu) {


        menu.setCook(userService.getUser(menuInputDto.cookId));

        Set<User> customers = new HashSet<>();
        if (menuInputDto.customersId != null) {
            for (int id :
                    menuInputDto.customersId) {

                User customer = userService.getUser((long) id);
                customers.add(customer);

            }
        }

        menu.setCustomers(customers);
        menu.setTitle(menuInputDto.title);
        menu.setStarter(menuInputDto.starter);
        menu.setMain(menuInputDto.main);
        menu.setSide(menuInputDto.side);
        menu.setDessert(menuInputDto.dessert);
        menu.setMenuDescription(menuInputDto.menuDescription);
        menu.setMenuPictureURL(menuInputDto.menuPictureURL);
        if (menuInputDto.menuType != null) {
            menu.setMenuType(MenuType.valueOf(menuInputDto.menuType));
        }
        menu.setWarmUpInstruction(menuInputDto.warmUpInstruction);
        menu.setOrderDeadline(menuInputDto.orderDeadline);
        menu.setStartDeliveryWindow(menuInputDto.startDeliveryWindow);
        menu.setEndDeliveryWindow(menuInputDto.endDeliveryWindow);
        menu.setNumberOfMenus(menuInputDto.numberOfMenus);
        menu.setPriceMenu(menuInputDto.priceMenu);
        menu.setTikkieLink(menuInputDto.tikkieLink);
        menu.setSendToCustomers(menuInputDto.sendToCustomers);

        return menu;


    }
}
