package nl.wondergem.wondercooks.dto.inputDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Validated
public class OrderInputDto {

    @NotNull
    private long menuId;
    @NotNull
    private long orderCustomerId;
    @NotNull
    private int numberOfMenus;
    private String allergies;
    private String allergiesExplanation;
    @NotNull
    private LocalTime startDeliveryWindow;
    @NotNull
    private LocalTime endDeliveryWindow;
    @NotNull
    private String streetAndNumber;
    @NotNull
    private String zipcode ;
    @NotNull
    private String city;
    private String comments;
    @NotNull
    private LocalDateTime orderDateAndTime;




}
