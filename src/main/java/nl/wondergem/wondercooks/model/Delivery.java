package nl.wondergem.wondercooks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private boolean paid;

    private LocalDateTime ETA;

    @OneToOne(mappedBy = "delivery", cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return id == delivery.id && paid == delivery.paid && Objects.equals(ETA, delivery.ETA) && Objects.equals(order, delivery.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paid, ETA, order);
    }
}
