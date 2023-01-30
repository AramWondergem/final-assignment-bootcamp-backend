package nl.wondergem.wondercooks.dto.inputDto;

import nl.wondergem.wondercooks.model.MenuType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotBlank;
import java.sql.Date;



@Validated
public class MenuDtoInput {
    @NotBlank
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
    public Date orderDeadline;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public Date startDeliveryWindow;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public Date endDeliveryWindow;
    public int numberOfMenus;
    public float priceMenu;
    public String tikkieLink;
}
