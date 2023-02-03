package nl.wondergem.wondercooks.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
public class DeliveryDto {


    private long id;

    private boolean paid;

    private LocalTime ETA;
}
