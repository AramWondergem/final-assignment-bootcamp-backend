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
    @Id
    @Email
    private String username; //is an emailadres to make

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

}