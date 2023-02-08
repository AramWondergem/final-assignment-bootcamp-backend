package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.OrderDto;
import nl.wondergem.wondercooks.dto.inputDto.OrderInputDto;
import nl.wondergem.wondercooks.exception.BadRequestException;
import nl.wondergem.wondercooks.mapper.DeliveryMapper;
import nl.wondergem.wondercooks.mapper.OrderMapper;
import nl.wondergem.wondercooks.model.Delivery;
import nl.wondergem.wondercooks.model.Order;
import nl.wondergem.wondercooks.repository.DeliveryRepository;
import nl.wondergem.wondercooks.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final DeliveryService deliveryService;

    private final DeliveryMapper deliveryMapper;

    private final EmailServiceImpl emailService;

    private final DeliveryRepository deliveryRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, DeliveryService deliveryService, DeliveryMapper deliveryMapper, EmailServiceImpl emailService, DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.deliveryService = deliveryService;
        this.deliveryMapper = deliveryMapper;
        this.emailService = emailService;
        this.deliveryRepository = deliveryRepository;
    }


    public OrderDto saveOrder(OrderInputDto orderInputDto) throws Exception {


        Order emptyOrder = new Order();

        Order order = orderMapper.orderInputDtoToOrder(orderInputDto, emptyOrder);

        if (orderInputDto.getOrderDateAndTime().isBefore(order.getMenu().getOrderDeadline())) {

            Order orderReturned = orderRepository.save(order);
            return orderMapper.orderToOrderDto(orderReturned);

        } else {
            throw new BadRequestException("The order deadline already past");
        }

    }

    public OrderDto getOrder(long id) {
        return orderMapper.orderToOrderDto(orderRepository.getReferenceById(id));
    }

    public void updateOrder(OrderInputDto orderInputDto, long id) {

        Order orderToBeUpdated = orderRepository.getReferenceById(id);

        if ( LocalDateTime.now().isBefore(orderToBeUpdated.getMenu().getOrderDeadline()) || orderInputDto.getNumberOfMenus() >= orderToBeUpdated.getNumberOfMenus()) {

            Order updatedOrder = orderMapper.orderInputDtoToOrderUpdate(orderInputDto, orderToBeUpdated);

            orderRepository.save(updatedOrder);
        } else {
            throw new BadRequestException("You can not lower the amount of menu's.");
        }

    }

    public void deleteOrder(long id) {

        OrderDto orderToBeDeleted = getOrder(id);

        if (LocalDateTime.now().isBefore(orderToBeDeleted.getMenu().orderDeadline)) {
            orderRepository.deleteById(id);
            if (orderToBeDeleted.getDelivery() != null) {
                deliveryService.deleteDelivery(orderToBeDeleted.getDelivery().getId());
            }
        } else {
            throw new BadRequestException("You can only delete a order when it is before the orderdeadline. The cook already bought groceries");
        }


    }

    public void acceptOrder(long id) {
        Order order = orderRepository.getReferenceById(id);

        //if there is already delivery, the order is already accepted
        if (order.getDelivery() == null) {
            Delivery emptyDelivery = new Delivery();
            Delivery result = deliveryRepository.save(emptyDelivery);


            order.setDelivery(result);
            order.setDeclined(false);
            orderRepository.save(order);
        }

    }

    public void declineOrder(long id) {
        Order order = orderRepository.getReferenceById(id);
        order.setDeclined(true);

        if(order.getDelivery() != null) {

            Delivery delivery = order.getDelivery();
            order.setDelivery(null);

            deliveryService.deleteDelivery(delivery.getId());

        }

        orderRepository.save(order);


    }

}
