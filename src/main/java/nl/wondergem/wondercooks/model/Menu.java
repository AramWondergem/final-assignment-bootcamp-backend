package nl.wondergem.wondercooks.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnore
    private User cook;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "customer_menu",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Set<User> customers;

    private String title;
    private String starter;
    private String main;
    private String side;
    private String dessert;
    private String menuDescription;
    private String menuPictureURL;
    @Enumerated(EnumType.STRING)
    private MenuType menuType;
    private String warmUpInstruction;
    private LocalDateTime orderDeadline;
    private LocalDateTime startDeliveryWindow;
    private LocalDateTime endDeliveryWindow;
    private int numberOfMenus;
    private float priceMenu;
    private String tikkieLink;
    private boolean sendToCustomers;



}
