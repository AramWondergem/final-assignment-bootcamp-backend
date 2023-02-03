package nl.wondergem.wondercooks.dto;

import nl.wondergem.wondercooks.model.MenuType;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;

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
}
