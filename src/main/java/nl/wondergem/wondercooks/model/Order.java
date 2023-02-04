package nl.wondergem.wondercooks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonIgnore
    private Menu menu;

    @ManyToOne
    private User orderCustomer;

    private int numberOfMenus;
    private String allergies;
    private String allergiesExplanation;
    private LocalTime startDeliveryWindow;
    private LocalTime endDeliveryWindow;
    private String streetAndNumber;
    private String zipcode ;
    private String city;
    private String comments;
    private LocalDateTime orderDateAndTime;

    @OneToOne
    @JsonIgnore
    private Delivery delivery;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && numberOfMenus == order.numberOfMenus && Objects.equals(menu, order.menu) && Objects.equals(orderCustomer, order.orderCustomer) && Objects.equals(allergies, order.allergies) && Objects.equals(allergiesExplanation, order.allergiesExplanation) && Objects.equals(startDeliveryWindow, order.startDeliveryWindow) && Objects.equals(endDeliveryWindow, order.endDeliveryWindow) && Objects.equals(streetAndNumber, order.streetAndNumber) && Objects.equals(zipcode, order.zipcode) && Objects.equals(city, order.city) && Objects.equals(comments, order.comments) && Objects.equals(orderDateAndTime, order.orderDateAndTime) && Objects.equals(delivery, order.delivery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menu, orderCustomer, numberOfMenus, allergies, allergiesExplanation, startDeliveryWindow, endDeliveryWindow, streetAndNumber, zipcode, city, comments, orderDateAndTime, delivery);
    }
}
