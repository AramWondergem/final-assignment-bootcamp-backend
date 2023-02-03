package nl.wondergem.wondercooks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

}
