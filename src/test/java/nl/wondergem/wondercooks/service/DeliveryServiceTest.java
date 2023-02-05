package nl.wondergem.wondercooks.service;

import nl.wondergem.wondercooks.dto.DeliveryDto;
import nl.wondergem.wondercooks.mapper.DeliveryMapper;
import nl.wondergem.wondercooks.model.Delivery;
import nl.wondergem.wondercooks.model.EmailDetails;
import nl.wondergem.wondercooks.model.Order;
import nl.wondergem.wondercooks.model.User;
import nl.wondergem.wondercooks.repository.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryMapper deliveryMapper;

    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private DeliveryService deliveryService;

    private DeliveryDto deliveryDto;
    private DeliveryDto deliveryDtoReturn;

    private Delivery delivery;

    private Order order;

    private User user;




    @BeforeEach
    void setUp() {
        deliveryDto = new DeliveryDto();
        deliveryDto.setETA(LocalTime.of(17, 0));
        deliveryDto.setPaid(false);

        deliveryDtoReturn = new DeliveryDto();
        deliveryDtoReturn.setETA(LocalTime.of(17, 0));
        deliveryDtoReturn.setPaid(false);
        deliveryDtoReturn.setId(1);

        delivery = new Delivery();
        delivery.setPaid(false);
        delivery.setETA(LocalTime.of(17,0));
        delivery.setId(1);

        user = new User();
        user.setId(1);
        user.setUsername("cook");

        order = new Order();
        order.setId(1);
        order.setOrderCustomer(user);
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
    }

    @Test
    void saveDelivery() {
        //arrange
        Delivery emptyDelivery = new Delivery();

        when(deliveryMapper.deliveryDtoToDelivery(deliveryDto, emptyDelivery)).thenReturn(delivery);
        when(deliveryRepository.save(delivery)).thenReturn(delivery);
        when(deliveryMapper.deliveryToDeliveryDto(delivery)).thenReturn(deliveryDtoReturn);

        //act
        DeliveryDto result = deliveryService.saveDelivery(deliveryDto);

        //assert
        verify(deliveryMapper, times(1)).deliveryDtoToDelivery(deliveryDto, emptyDelivery);
        verify(deliveryRepository, times(1)).save(delivery);
        verify(deliveryMapper,times(1)).deliveryToDeliveryDto(delivery);
        assertEquals(deliveryDtoReturn.getId(),result.getId());
        assertEquals(deliveryDtoReturn.getETA(),result.getETA());
        assertEquals(deliveryDtoReturn.isPaid(),result.isPaid());

    }

    @Test
    void getDelivery() {
        //arrange
        when(deliveryRepository.getReferenceById((long)1)).thenReturn(delivery);
        when(deliveryMapper.deliveryToDeliveryDto(delivery)).thenReturn(deliveryDtoReturn);
        //act
        DeliveryDto result = deliveryService.getDelivery(1);

        //assert
        verify(deliveryRepository, times(1)).getReferenceById((long)1);
        verify(deliveryMapper,times(1)).deliveryToDeliveryDto(delivery);

    }

    @Test
    void updateDelivery() {
        //arrange
        Delivery updatedDelivery = new Delivery();
        updatedDelivery = delivery;
        updatedDelivery.setPaid(true);

        deliveryDto.setPaid(true);

        when(deliveryRepository.getReferenceById((long)1)).thenReturn(delivery);
        when(deliveryMapper.deliveryDtoToDelivery(deliveryDto, delivery)).thenReturn(updatedDelivery);
        //act

        deliveryService.updateDelivery(deliveryDto, 1);

        //assert
        verify(deliveryRepository, times(1)).getReferenceById((long)1 );
        verify(deliveryMapper, times(1)).deliveryDtoToDelivery(deliveryDto, delivery);
        verify(deliveryRepository,times(1)).save(updatedDelivery);
    }

    @Test
    void deleteDelivery() {
        //act
        deliveryService.deleteDelivery(1);
        //assert
        verify(deliveryRepository).deleteById((long)1);

    }

    @Test
    void sendETA() {
        //arrange
        delivery.setOrder(order);

        when(deliveryRepository.getReferenceById((long)1)).thenReturn(delivery);

        EmailDetails emailDetails = new EmailDetails("wonderreclame@gmail.com", "Dear " + delivery.getOrder().getOrderCustomer().getUsername() + "\nThe ETA and warm up instructions are in your account", "ETA and warm up instruction");

        //act
        deliveryService.sendETA(1);

        //assert
        verify(deliveryRepository, times(1)).getReferenceById((long)1 );
        verify(emailService,times(1)).sendSimpleMail(emailDetails);

    }
}