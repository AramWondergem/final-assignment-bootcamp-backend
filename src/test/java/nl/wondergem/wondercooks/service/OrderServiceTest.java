package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.MenuDto;
import nl.wondergem.wondercooks.dto.MenuDtoSmall;
import nl.wondergem.wondercooks.dto.OrderDto;
import nl.wondergem.wondercooks.dto.UserDtoSmall;
import nl.wondergem.wondercooks.dto.inputDto.OrderInputDto;
import nl.wondergem.wondercooks.mapper.OrderMapper;
import nl.wondergem.wondercooks.model.Menu;
import nl.wondergem.wondercooks.model.MenuType;
import nl.wondergem.wondercooks.model.Order;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private OrderInputDto orderInputDto;

    private Order order;

    private OrderDto orderDto;
    private Menu menu;

    private User user;

    private User user1;
    private User user2;

    private MenuDtoSmall menuDtoSmall;
    private UserDtoSmall userDtoSmall;
    private UserDtoSmall userDtoSmall1;

    @BeforeEach
    public void setup() {
        orderInputDto = new OrderInputDto();


        orderInputDto.setMenuId(1);
        orderInputDto.setOrderCustomerId(2);
        orderInputDto.setNumberOfMenus(2);
        orderInputDto.setAllergies("pinda");
        orderInputDto.setAllergiesExplanation("I will die");
        orderInputDto.setStreetAndNumber("dorpsstraat 1");
        orderInputDto.setStartDeliveryWindow(LocalTime.of(17, 0));
        orderInputDto.setEndDeliveryWindow(LocalTime.of(18, 0));
        orderInputDto.setZipcode("1412ZZ");
        orderInputDto.setCity("City");
        orderInputDto.setComments("hallo");
        orderInputDto.setOrderDateAndTime(LocalDateTime.of(2020, 10, 10, 17, 0));

        user = new User();
        user.setId(1);
        user.setUsername("cook");

        user1 = new User();
        user1.setId(2);
        user1.setUsername("customer");

        user2 = new User();
        user2.setId(3);
        user2.setUsername("customer");

        Set<User> customersEntity = new HashSet<>();
        customersEntity.add(user1);
        customersEntity.add(user2);

        menu = new Menu();
        menu.setId(1);
        menu.setCook(user);
        menu.setCustomers(customersEntity);
        menu.setTitle("Best Title ever");
        menu.setStarter("starter");
        menu.setMain("main");
        menu.setSide("side");
        menu.setDessert("dessert");
        menu.setMenuDescription("menu description");
        menu.setMenuPictureURL("menu url");
        menu.setMenuType(MenuType.VEGAN);
        menu.setWarmUpInstruction("warm-up instruction");
        menu.setOrderDeadline(LocalDateTime.of(2022, 12, 24, 17, 0));
        menu.setStartDeliveryWindow(LocalDateTime.of(2022, 12, 25, 17, 0));
        menu.setEndDeliveryWindow(LocalDateTime.of(2022, 12, 25, 19, 0));
        menu.setNumberOfMenus(30);
        menu.setPriceMenu(12.50f);
        menu.setTikkieLink("www.tikkie.nl");
        menu.setSendToCustomers(false);

        userDtoSmall = new UserDtoSmall();
        userDtoSmall.id = 1;
        userDtoSmall.username = "cook";

        userDtoSmall1 = new UserDtoSmall();
        userDtoSmall.id = 2;
        userDtoSmall.username = "orderCustomer";



        menuDtoSmall = new MenuDtoSmall();
        menuDtoSmall.id = 1;
        menuDtoSmall.title = "Best Title ever";
        menuDtoSmall.starter = "starter";
        menuDtoSmall.main = "main";
        menuDtoSmall.side = "side";
        menuDtoSmall.dessert = "dessert";
        menuDtoSmall.menuDescription = "menu description";
        menuDtoSmall.menuPictureURL = "menu url";
        menuDtoSmall.menuType = MenuType.VEGAN;
        menuDtoSmall.warmUpInstruction = "warm-up instruction";
        menuDtoSmall.orderDeadline = LocalDateTime.of(2022, 12, 24, 17, 0);
        menuDtoSmall.startDeliveryWindow = LocalDateTime.of(2022, 12, 25, 17, 0);
        menuDtoSmall.endDeliveryWindow = LocalDateTime.of(2022, 12, 25, 19, 0);
        menuDtoSmall.numberOfMenus = 30;
        menuDtoSmall.priceMenu = 12.50f;
        menuDtoSmall.tikkieLink = "www.tikkie.nl";
        menuDtoSmall.sendToCustomers = false;

        order = new Order();
        order.setId(1);
        order.setMenu(menu);
        order.setOrderCustomer(user1);
        order.setNumberOfMenus(2);
        order.setAllergies("pinda");
        order.setAllergiesExplanation("I will die");
        order.setStartDeliveryWindow(LocalTime.of(17, 0));
        order.setEndDeliveryWindow(LocalTime.of(18, 0));
        order.setStreetAndNumber("dorpsstraat 1");
        order.setZipcode("1412ZZ");
        order.setCity("City");
        order.setComments("hallo");
        order.setOrderDateAndTime(LocalDateTime.of(2020, 10, 10, 17, 0));

        orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setMenu(menuDtoSmall);
        orderDto.setOrderCustomer(userDtoSmall1);
        orderDto.setNumberOfMenus(2);
        orderDto.setAllergies("pinda");
        orderDto.setAllergiesExplanation("I will die");
        orderDto.setStartDeliveryWindow(LocalTime.of(17, 0));
        orderDto.setEndDeliveryWindow(LocalTime.of(18, 0));
        orderDto.setStreetAndNumber("dorpsstraat 1");
        orderDto.setZipcode("1412ZZ");
        orderDto.setCity("City");
        orderDto.setComments("hallo");
        orderDto.setOrderDateAndTime(LocalDateTime.of(2020, 10, 10, 17, 0));
        orderDto.setDelivery(null);
    }


    @Test
    void saveOrder() {
        //arrange
        Order emptyOrder = new Order();
        when(orderMapper.orderInputDtoToOrder(orderInputDto, emptyOrder)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.orderToOrderDto(order)).thenReturn(orderDto);

        //act

        OrderDto result = orderService.saveOrder(orderInputDto);

        //assert
        verify(orderMapper, times(1)).orderInputDtoToOrder(orderInputDto,emptyOrder);
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).orderToOrderDto(order);
        assertEquals(1, result.getId());
        assertEquals(menuDtoSmall,result.getMenu());
        assertEquals(userDtoSmall1,result.getOrderCustomer());
        assertEquals(LocalDateTime.of(2020, 10, 10, 17, 0),result.getOrderDateAndTime());

    }

    @Test
    void getOrder() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void deleteOrder() {
    }
}