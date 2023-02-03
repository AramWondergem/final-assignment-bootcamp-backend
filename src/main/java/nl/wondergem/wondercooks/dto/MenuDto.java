package nl.wondergem.wondercooks.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.wondergem.wondercooks.model.MenuType;
import nl.wondergem.wondercooks.model.User;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

public class MenuDto {
    public long id;
    public UserDtoSmall cook;
    public Collection<UserDtoSmall> customers;
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

    public Collection<OrderDtoSmall> orders;
}
