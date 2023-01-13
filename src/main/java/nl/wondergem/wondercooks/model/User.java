package nl.wondergem.wondercooks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="users")
public class User {

    private String username;


    @Id
    @Email
    private String email; //e-mailadres is identifier

    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public void addRole(Role role) {
        roles.add(role);
    }

}