package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.DeliveryDto;
import nl.wondergem.wondercooks.mapper.DeliveryMapper;
import nl.wondergem.wondercooks.model.Delivery;
import nl.wondergem.wondercooks.model.EmailDetails;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.DeliveryRepository;
import org.springframework.stereotype.Service;



@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final EmailServiceImpl emailService;

    private final DeliveryMapper deliveryMapper;

    public DeliveryService(DeliveryRepository deliveryRepository, EmailServiceImpl emailService, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.emailService = emailService;
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


        deliveryRepository.deleteById(id);


    }

    public void sendETA(long id){
        Delivery delivery = deliveryRepository.getReferenceById(id);

        User customer = delivery.getOrder().getOrderCustomer();

        EmailDetails emailDetails = new EmailDetails("wonderreclame@gmail.com", "Dear " + customer.getUsername() + "\nThe ETA and warm up instructions are in your account", "ETA and warm up instruction");
        emailService.sendSimpleMail(emailDetails);

    }

}
