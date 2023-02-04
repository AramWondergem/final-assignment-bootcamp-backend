package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.MenuDto;
import nl.wondergem.wondercooks.dto.OrderDto;
import nl.wondergem.wondercooks.dto.inputDto.MenuInputDto;
import nl.wondergem.wondercooks.dto.inputDto.OrderInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.mapper.OrderMapper;
import nl.wondergem.wondercooks.model.Menu;
import nl.wondergem.wondercooks.model.Order;
import nl.wondergem.wondercooks.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }


    public OrderDto saveOrder(OrderInputDto orderInputDto) throws Exception{


        Order emptyOrder = new Order();

        Order order = orderMapper.orderInputDtoToOrder(orderInputDto, emptyOrder);

        if(orderInputDto.getOrderDateAndTime().isBefore(order.getMenu().getOrderDeadline())) {

            Order orderReturned = orderRepository.save(order);
            return orderMapper.orderToOrderDto(orderReturned);

        } else {
            throw new BadRequestException("The order deadline already past");
        }

    }

    public OrderDto getOrder(long id) {
        return orderMapper.orderToOrderDto(orderRepository.getReferenceById(id));
    }

    public void updateOrder(OrderInputDto orderInputDto,long id){

        Order orderToBeUpdated = orderRepository.getReferenceById(id);

        if(orderInputDto.getNumberOfMenus() >= orderToBeUpdated.getNumberOfMenus()) {

            Order updatedOrder = orderMapper.orderInputDtoToOrder(orderInputDto, orderToBeUpdated);

            orderRepository.save(updatedOrder);
        } else {
            throw new BadRequestException("You can not lower the amount of menu's.");
        }

    }

    public void deleteOrder(long id){

        OrderDto orderToBeDeleted = getOrder(id);

        if(LocalDateTime.now().isBefore(orderToBeDeleted.getMenu().orderDeadline)) {
            orderRepository.deleteById(id);
        } else {
            throw new BadRequestException("You can only delete a order when it is before the orderdeadline. The cook already bought groceries");
        }


    }

}
