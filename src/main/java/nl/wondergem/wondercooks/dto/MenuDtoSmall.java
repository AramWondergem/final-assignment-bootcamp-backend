package nl.wondergem.wondercooks.dto;

import nl.wondergem.wondercooks.model.MenuType;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

public class MenuDtoSmall {
    public long id;
    public String title;
    public String starter;
    public String main;
    public String side;
    public String dessert;
    public String menuDescription;
    public String menuPictureURL;
    public MenuType menuType;
    public String warmUpInstruction;
    public LocalDateTime orderDeadline;
    public LocalDateTime startDeliveryWindow;
    public LocalDateTime endDeliveryWindow;
    public int numberOfMenus;
    public float priceMenu;
    public String tikkieLink;
    public boolean sendToCustomers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDtoSmall that = (MenuDtoSmall) o;
        return id == that.id && numberOfMenus == that.numberOfMenus && Float.compare(that.priceMenu, priceMenu) == 0 && sendToCustomers == that.sendToCustomers && Objects.equals(title, that.title) && Objects.equals(starter, that.starter) && Objects.equals(main, that.main) && Objects.equals(side, that.side) && Objects.equals(dessert, that.dessert) && Objects.equals(menuDescription, that.menuDescription) && Objects.equals(menuPictureURL, that.menuPictureURL) && menuType == that.menuType && Objects.equals(warmUpInstruction, that.warmUpInstruction) && Objects.equals(orderDeadline, that.orderDeadline) && Objects.equals(startDeliveryWindow, that.startDeliveryWindow) && Objects.equals(endDeliveryWindow, that.endDeliveryWindow) && Objects.equals(tikkieLink, that.tikkieLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, starter, main, side, dessert, menuDescription, menuPictureURL, menuType, warmUpInstruction, orderDeadline, startDeliveryWindow, endDeliveryWindow, numberOfMenus, priceMenu, tikkieLink, sendToCustomers);
    }
}
