package nl.wondergem.wondercooks.mapper;


import nl.wondergem.wondercooks.dto.OrderDto;
import nl.wondergem.wondercooks.dto.OrderDtoSmall;
import nl.wondergem.wondercooks.dto.inputDto.OrderInputDto;
import nl.wondergem.wondercooks.model.Order;
import nl.wondergem.wondercooks.repository.MenuRepository;
import nl.wondergem.wondercooks.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final MenuMapper menuMapper;
    private final UserMapper userMapper;

    private final DeliveryMapper deliveryMapper;

    private final MenuRepository menuRepository;

    private final UserRepository userRepository;

    public OrderMapper(MenuMapper menuMapper, @Lazy UserMapper userMapper, DeliveryMapper deliveryMapper, MenuRepository menuRepository, UserRepository userRepository) {
        this.menuMapper = menuMapper;
        this.userMapper = userMapper;
        this.deliveryMapper = deliveryMapper;
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }

    public OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setMenu(menuMapper.menuToMenuDtoSmall(order.getMenu()));
        orderDto.setOrderCustomer(userMapper.userToUserDtoSmall(order.getOrderCustomer()));
        orderDto.setNumberOfMenus(order.getNumberOfMenus());
        orderDto.setAllergies(order.getAllergies());
        orderDto.setAllergiesExplanation(order.getAllergiesExplanation());
        orderDto.setStartDeliveryWindow(order.getStartDeliveryWindow());
        orderDto.setEndDeliveryWindow(order.getEndDeliveryWindow());
        orderDto.setStreetAndNumber(order.getStreetAndNumber());
        orderDto.setZipcode(order.getZipcode());
        orderDto.setCity(order.getCity());
        orderDto.setComments(order.getComments());
        orderDto.setOrderDateAndTime(order.getOrderDateAndTime());
        orderDto.setDelivery(deliveryMapper.DeliveryToDeliveryDto(order.getDelivery()));
    return orderDto;
    }

    public OrderDtoSmall orderToOrderDtoSmall(Order order){
        OrderDtoSmall orderDtoSmall = new OrderDtoSmall();
        orderDtoSmall.setId(order.getId());
        orderDtoSmall.setNumberOfMenus(order.getNumberOfMenus());
        orderDtoSmall.setAllergies(order.getAllergies());
        orderDtoSmall.setAllergiesExplanation(order.getAllergiesExplanation());
        orderDtoSmall.setStartDeliveryWindow(order.getStartDeliveryWindow());
        orderDtoSmall.setEndDeliveryWindow(order.getEndDeliveryWindow());
        orderDtoSmall.setStreetAndNumber(order.getStreetAndNumber());
        orderDtoSmall.setZipcode(order.getZipcode());
        orderDtoSmall.setCity(order.getCity());
        orderDtoSmall.setComments(order.getComments());
        orderDtoSmall.setOrderDateAndTime(order.getOrderDateAndTime());
        return orderDtoSmall;


    }

    public Order orderInputDtoToOrder(OrderInputDto orderInputDto, Order order){

        order.setMenu(menuRepository.getReferenceById(orderInputDto.getMenuId()));
        order.setOrderCustomer(userRepository.getReferenceById(orderInputDto.getOrderCustomerId()));
        order.setNumberOfMenus(orderInputDto.getNumberOfMenus());
        order.setAllergies(orderInputDto.getAllergies());
        order.setAllergiesExplanation(orderInputDto.getAllergiesExplanation());
        order.setStartDeliveryWindow(orderInputDto.getStartDeliveryWindow());
        order.setEndDeliveryWindow(orderInputDto.getEndDeliveryWindow());
        order.setStreetAndNumber(orderInputDto.getStreetAndNumber());
        order.setZipcode(orderInputDto.getZipcode());
        order.setCity(orderInputDto.getCity());
        order.setComments(orderInputDto.getComments());
        order.setOrderDateAndTime(orderInputDto.getOrderDateAndTime());

        return order;

    }

    public Order orderInputDtoToOrderUpdate(OrderInputDto orderInputDto, Order order){

        order.setMenu(menuRepository.getReferenceById(orderInputDto.getMenuId()));
        order.setOrderCustomer(userRepository.getReferenceById(orderInputDto.getOrderCustomerId()));
        order.setNumberOfMenus(orderInputDto.getNumberOfMenus());
        order.setAllergies(orderInputDto.getAllergies());
        order.setAllergiesExplanation(orderInputDto.getAllergiesExplanation());
        order.setStartDeliveryWindow(orderInputDto.getStartDeliveryWindow());
        order.setEndDeliveryWindow(orderInputDto.getEndDeliveryWindow());
        order.setStreetAndNumber(orderInputDto.getStreetAndNumber());
        order.setZipcode(orderInputDto.getZipcode());
        order.setCity(orderInputDto.getCity());
        order.setComments(orderInputDto.getComments());

        return order;

    }
}
