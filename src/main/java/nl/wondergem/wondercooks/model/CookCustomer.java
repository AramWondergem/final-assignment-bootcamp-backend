package nl.wondergem.wondercooks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name="cooks_customers")
public class CookCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User cook;

    @ManyToOne
    private User customer;

}
