package nl.wondergem.wondercooks.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "menus")
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

    @OneToMany(mappedBy = "menu")
    @JsonIgnore
    private Set<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return id == menu.id && numberOfMenus == menu.numberOfMenus && Float.compare(menu.priceMenu, priceMenu) == 0 && sendToCustomers == menu.sendToCustomers && Objects.equals(cook, menu.cook) && Objects.equals(customers, menu.customers) && Objects.equals(title, menu.title) && Objects.equals(starter, menu.starter) && Objects.equals(main, menu.main) && Objects.equals(side, menu.side) && Objects.equals(dessert, menu.dessert) && Objects.equals(menuDescription, menu.menuDescription) && Objects.equals(menuPictureURL, menu.menuPictureURL) && menuType == menu.menuType && Objects.equals(warmUpInstruction, menu.warmUpInstruction) && Objects.equals(orderDeadline, menu.orderDeadline) && Objects.equals(startDeliveryWindow, menu.startDeliveryWindow) && Objects.equals(endDeliveryWindow, menu.endDeliveryWindow) && Objects.equals(tikkieLink, menu.tikkieLink) && Objects.equals(orders, menu.orders);
    }


}
