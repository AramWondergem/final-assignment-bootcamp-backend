package nl.wondergem.wondercooks.mapper;

import nl.wondergem.wondercooks.dto.DeliveryDto;
import nl.wondergem.wondercooks.model.Delivery;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    public Delivery deliveryDtoToDelivery(DeliveryDto deliveryDto,Delivery delivery) {

        delivery.setETA(deliveryDto.getETA());
        delivery.setPaid(deliveryDto.isPaid());

        return delivery;

    }
    public DeliveryDto deliveryToDeliveryDto(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();

        deliveryDto.setETA(deliveryDto.getETA());
        deliveryDto.setPaid(deliveryDto.isPaid());

        return deliveryDto;

    }
}
