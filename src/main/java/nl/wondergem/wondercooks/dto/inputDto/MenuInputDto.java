package nl.wondergem.wondercooks.dto.inputDto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;


@Validated
public class MenuInputDto {
    @NotNull
    public int cookId;
    public int[] customersId;
    public String title;
    public String starter;
    public String main;
    public String side;
    public String dessert;

    public String menuDescription;
    public String menuPictureURL;
    public String menuType;
    public String warmUpInstruction;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime orderDeadline;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime startDeliveryWindow;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime endDeliveryWindow;
    public int numberOfMenus;
    public float priceMenu;
    public String tikkieLink;
    @NotNull
    public boolean sendToCustomers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuInputDto that = (MenuInputDto) o;
        return cookId == that.cookId && numberOfMenus == that.numberOfMenus && Float.compare(that.priceMenu, priceMenu) == 0 && sendToCustomers == that.sendToCustomers && Arrays.equals(customersId, that.customersId) && Objects.equals(title, that.title) && Objects.equals(starter, that.starter) && Objects.equals(main, that.main) && Objects.equals(side, that.side) && Objects.equals(dessert, that.dessert) && Objects.equals(menuDescription, that.menuDescription) && Objects.equals(menuPictureURL, that.menuPictureURL) && Objects.equals(menuType, that.menuType) && Objects.equals(warmUpInstruction, that.warmUpInstruction) && Objects.equals(orderDeadline, that.orderDeadline) && Objects.equals(startDeliveryWindow, that.startDeliveryWindow) && Objects.equals(endDeliveryWindow, that.endDeliveryWindow) && Objects.equals(tikkieLink, that.tikkieLink);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(cookId, title, starter, main, side, dessert, menuDescription, menuPictureURL, menuType, warmUpInstruction, orderDeadline, startDeliveryWindow, endDeliveryWindow, numberOfMenus, priceMenu, tikkieLink, sendToCustomers);
        result = 31 * result + Arrays.hashCode(customersId);
        return result;
    }
}
