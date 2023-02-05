package nl.wondergem.wondercooks.mapper;

import nl.wondergem.wondercooks.dto.DeliveryDto;
import nl.wondergem.wondercooks.model.Delivery;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    private final OrderMapper orderMapper;

    public DeliveryMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Delivery deliveryDtoToDelivery(DeliveryDto deliveryDto, Delivery delivery) {

        delivery.setETA(deliveryDto.getETA());
        delivery.setPaid(deliveryDto.isPaid());
        delivery.setId(deliveryDto.getId());


        return delivery;

    }
    public DeliveryDto deliveryToDeliveryDto(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();

        deliveryDto.setETA(deliveryDto.getETA());
        deliveryDto.setPaid(deliveryDto.isPaid());
        deliveryDto.setOrder(orderMapper.orderToOrderDto(delivery.getOrder()));

        return deliveryDto;

    }
}
