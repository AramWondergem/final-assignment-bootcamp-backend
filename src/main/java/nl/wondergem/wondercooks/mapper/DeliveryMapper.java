package nl.wondergem.wondercooks.mapper;

import nl.wondergem.wondercooks.dto.DeliveryDto;
import nl.wondergem.wondercooks.model.Delivery;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    private final OrderMapper orderMapper;

    public DeliveryMapper(@Lazy OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Delivery deliveryDtoToDelivery(DeliveryDto deliveryDto, Delivery delivery) {

        delivery.setETA(deliveryDto.getETA());
        delivery.setPaid(deliveryDto.isPaid());
        if(deliveryDto.getId()!=0) {
            delivery.setId(deliveryDto.getId());
        }


        return delivery;

    }
    public DeliveryDto deliveryToDeliveryDto(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();

        deliveryDto.setETA(deliveryDto.getETA());
        deliveryDto.setPaid(deliveryDto.isPaid());
        if(delivery.getOrder()!=null) {
            deliveryDto.setOrder(orderMapper.orderToOrderDto(delivery.getOrder()));
        }

        return deliveryDto;

    }

    public DeliveryDto deliveryToDeliveryDtoReturn(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();

        deliveryDto.setId(delivery.getId());
        deliveryDto.setETA(delivery.getETA());
        deliveryDto.setPaid(delivery.isPaid());

        return deliveryDto;

    }
}
