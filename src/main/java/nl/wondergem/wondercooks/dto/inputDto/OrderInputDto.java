package nl.wondergem.wondercooks.dto.inputDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

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
    private String zipcode;
    @NotNull
    private String city;
    private String comments;
    @NotNull
    private LocalDateTime orderDateAndTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderInputDto that = (OrderInputDto) o;
        return menuId == that.menuId && orderCustomerId == that.orderCustomerId && numberOfMenus == that.numberOfMenus && Objects.equals(allergies, that.allergies) && Objects.equals(allergiesExplanation, that.allergiesExplanation) && Objects.equals(startDeliveryWindow, that.startDeliveryWindow) && Objects.equals(endDeliveryWindow, that.endDeliveryWindow) && Objects.equals(streetAndNumber, that.streetAndNumber) && Objects.equals(zipcode, that.zipcode) && Objects.equals(city, that.city) && Objects.equals(comments, that.comments) && Objects.equals(orderDateAndTime, that.orderDateAndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, orderCustomerId, numberOfMenus, allergies, allergiesExplanation, startDeliveryWindow, endDeliveryWindow, streetAndNumber, zipcode, city, comments, orderDateAndTime);
    }

}
