package nl.wondergem.wondercooks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="users")
public class User {

    private String username;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    private String email; //e-mailadres is identifier

    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    private String streetAndNumber;
    private String zipcode;
    private String city;
    private String favoriteColour;
    private String allergies;
    private String allergiesExplanation;
    private String profilePicture;

    @OneToMany(mappedBy = "cook")
    private Set<CookCustomer> cookCustomerCookSide;

    @OneToMany(mappedBy = "customer")
    private Set<CookCustomer> cookCustomerCustomerSide;

    public void addRole(Role role) {
        roles.add(role);
    }



}