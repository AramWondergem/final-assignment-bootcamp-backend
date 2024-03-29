package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.*;
import nl.wondergem.wondercooks.dto.inputDto.OrderInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.mapper.DeliveryMapper;
import nl.wondergem.wondercooks.mapper.OrderMapper;
import nl.wondergem.wondercooks.model.*;
import nl.wondergem.wondercooks.repository.DeliveryRepository;
import nl.wondergem.wondercooks.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryMapper deliveryMapper;

    @InjectMocks
    private OrderService orderService;

    private OrderInputDto orderInputDto;

    private Order order;

    private Order updatedOrder;

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
        userDtoSmall1.id = 2;
        userDtoSmall1.username = "orderCustomer";


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


        updatedOrder = new Order();
        updatedOrder.setId(1);
        updatedOrder.setMenu(menu);
        updatedOrder.setOrderCustomer(user1);
        updatedOrder.setNumberOfMenus(2);
        updatedOrder.setAllergies("Amandelen");
        updatedOrder.setAllergiesExplanation("I will die");
        updatedOrder.setStartDeliveryWindow(LocalTime.of(17, 0));
        updatedOrder.setEndDeliveryWindow(LocalTime.of(18, 0));
        updatedOrder.setStreetAndNumber("dorpsstraat 1");
        updatedOrder.setZipcode("1412ZZ");
        updatedOrder.setCity("City");
        updatedOrder.setComments("hallo");
        updatedOrder.setOrderDateAndTime(LocalDateTime.of(2020, 10, 10, 17, 0));

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
    @DisplayName("WhenAllFieldsAreFilledCorrectlyThenAOrderDtoIsReturned")
    void saveOrder() throws Exception {
        //arrange
        Order emptyOrder = new Order();
        when(orderMapper.orderInputDtoToOrder(orderInputDto, emptyOrder)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.orderToOrderDto(order)).thenReturn(orderDto);

        //act

        OrderDto result = orderService.saveOrder(orderInputDto);

        //assert
        verify(orderMapper, times(1)).orderInputDtoToOrder(orderInputDto, emptyOrder);
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).orderToOrderDto(order);
        assertEquals(1, result.getId());
        assertEquals(menuDtoSmall, result.getMenu());
        assertEquals(userDtoSmall1, result.getOrderCustomer());
        assertEquals(LocalDateTime.of(2020, 10, 10, 17, 0), result.getOrderDateAndTime());

    }

    @Test
    @DisplayName("WhenOrderDeadlineIsPastedThenABadRequestExceptionWillBeThrown")
    void saveOrderAfterDeadline() {
        //arrange
        Order emptyOrder = new Order();
        orderInputDto.setOrderDateAndTime(LocalDateTime.of(2026, 10, 10, 17, 0));
        when(orderMapper.orderInputDtoToOrder(orderInputDto, emptyOrder)).thenReturn(order);

        //act

        Exception exception = assertThrows(BadRequestException.class, () -> orderService.saveOrder(orderInputDto));

        //assert
        verify(orderMapper, times(1)).orderInputDtoToOrder(orderInputDto, emptyOrder);
        assertEquals("The order deadline already past", exception.getMessage());

    }

    @Test
    void getOrder() {
        // arrange
        when(orderRepository.getReferenceById((long) 1)).thenReturn(order);
        when(orderMapper.orderToOrderDto(order)).thenReturn(orderDto);
        //act
        OrderDto result = orderService.getOrder(1);

        //assert
        assertEquals(orderDto.getId(), result.getId());
        assertEquals(orderDto.getMenu(), result.getMenu());
        assertEquals(orderDto.getOrderCustomer(), result.getOrderCustomer());
    }

    @Test
    void updateOrder() {
        //arrange
        orderInputDto.setAllergies("amandelen");
        when(orderRepository.getReferenceById((long) 1)).thenReturn(order);
        when(orderMapper.orderInputDtoToOrderUpdate(orderInputDto, order)).thenReturn(updatedOrder);
        //act
        orderService.updateOrder(orderInputDto, 1);
        //assert
        verify(orderRepository, times(1)).getReferenceById((long) 1);
        verify(orderMapper, times(1)).orderInputDtoToOrderUpdate(orderInputDto, order);
        verify(orderRepository, times(1)).save(updatedOrder);
    }

    @Test
    void updateOrderWhenNumberOfMenusIsLowerInOrderInputDto() {
        //arrange
        orderInputDto.setAllergies("amandelen");
        orderInputDto.setNumberOfMenus(1);
        when(orderRepository.getReferenceById((long) 1)).thenReturn(order);
        //act
        Exception exception = assertThrows(BadRequestException.class, () -> orderService.updateOrder(orderInputDto, 1));
        //assert
        verify(orderRepository, times(1)).getReferenceById((long) 1);
        assertEquals("You can not lower the amount of menu's.", exception.getMessage());
    }

    @Test
    void deleteOrder() {
        //arrange

        menuDtoSmall.orderDeadline = LocalDateTime.now().plusDays(1);

        when(orderRepository.getReferenceById((long) 1)).thenReturn(order);
        when(orderMapper.orderToOrderDto(order)).thenReturn(orderDto);
        //act
        orderService.deleteOrder(1);

        //assert
        verify(orderRepository, times(1)).deleteById((long) 1);
    }

    @Test
    void deleteOrderWhenOrderDeadlineIsPassed() {
        //arrange
        when(orderRepository.getReferenceById((long) 1)).thenReturn(order);
        when(orderMapper.orderToOrderDto(order)).thenReturn(orderDto);
        //act
        Exception exception = assertThrows(BadRequestException.class, () -> orderService.deleteOrder(1));

        //assert
        assertEquals("You can only delete a order when it is before the orderdeadline. The cook already bought groceries", exception.getMessage());
    }

    @Test
    void acceptOrder() {
        //arrange

        Delivery emptyDelivery = new Delivery();

        Delivery result = new Delivery();
        result.setId(1);

        Order savedOrder = new Order();
        savedOrder.setId(1);
        savedOrder.setMenu(menu);
        savedOrder.setOrderCustomer(user1);
        savedOrder.setNumberOfMenus(2);
        savedOrder.setAllergies("pinda");
        savedOrder.setAllergiesExplanation("I will die");
        savedOrder.setStartDeliveryWindow(LocalTime.of(17, 0));
        savedOrder.setEndDeliveryWindow(LocalTime.of(18, 0));
        savedOrder.setStreetAndNumber("dorpsstraat 1");
        savedOrder.setZipcode("1412ZZ");
        savedOrder.setCity("City");
        savedOrder.setComments("hallo");
        savedOrder.setOrderDateAndTime(LocalDateTime.of(2020, 10, 10, 17, 0));
        savedOrder.setDelivery(result);
        savedOrder.setDeclined(false);

        when(orderRepository.getReferenceById((long) 1)).thenReturn(order);
        when(deliveryRepository.save(emptyDelivery)).thenReturn(result);

        //act

        orderService.acceptOrder(1);

        //assert
        verify(orderRepository, times(1)).getReferenceById((long) 1);
        verify(deliveryRepository, times(1)).save(emptyDelivery);
        verify(orderRepository, times(1)).save(order);


    }

    @Test
    void declineOrder() {
        //arrange
        when(orderRepository.getReferenceById((long) 1)).thenReturn(order);
        order.isDeclined();

        //act
        orderService.declineOrder(1);

        //assert
        verify(orderRepository, times(1)).getReferenceById((long) 1);
        verify(orderRepository, times(1)).save(order);

    }
}