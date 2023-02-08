package nl.wondergem.wondercooks.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class OrderDto {

    private long id;
    private MenuDtoSmall menu;
    private UserDtoSmall orderCustomer;

    private int numberOfMenus;
    private String allergies;
    private String allergiesExplanation;
    private LocalTime startDeliveryWindow;
    private LocalTime endDeliveryWindow;
    private String streetAndNumber;
    private String zipcode;
    private String city;
    private String comments;
    private LocalDateTime orderDateAndTime;
    private DeliveryDto delivery;
    private boolean declined;
}
