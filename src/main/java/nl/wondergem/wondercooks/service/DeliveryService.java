package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.DeliveryDto;
import nl.wondergem.wondercooks.mapper.DeliveryMapper;
import nl.wondergem.wondercooks.model.Delivery;
import nl.wondergem.wondercooks.repository.DeliveryRepository;
import org.springframework.stereotype.Service;



@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    private final DeliveryMapper deliveryMapper;

    public DeliveryService(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }


    public DeliveryDto saveDelivery(DeliveryDto deliveryDto)  {


        Delivery emptyDelivery = new Delivery();

        Delivery delivery = deliveryMapper.deliveryDtoToDelivery(deliveryDto, emptyDelivery);

        Delivery deliveryReturned = deliveryRepository.save(delivery);

        return deliveryMapper.deliveryToDeliveryDto(deliveryReturned);


    }

    public DeliveryDto getDelivery(long id) {
        return deliveryMapper.deliveryToDeliveryDto(deliveryRepository.getReferenceById(id));
    }

    public void updateDelivery(DeliveryDto deliveryDto, long id) {

        Delivery deliveryToBeUpdated = deliveryRepository.getReferenceById(id);


        Delivery updatedDelivery = deliveryMapper.deliveryDtoToDelivery(deliveryDto, deliveryToBeUpdated);

        deliveryRepository.save(updatedDelivery);

    }

    public void deleteDelivery(long id) {

        DeliveryDto deliveryToBeDeleted = getDelivery(id);

        deliveryRepository.deleteById(id);


    }
}
