package nl.wondergem.wondercooks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name="users")
public class User {

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Id
    @Email
    private String username; //e-mailadres is identifier

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    public void addRole(Role role) {
        if(!roles.contains(role)) {
            roles.add(role);
        }
    }

}