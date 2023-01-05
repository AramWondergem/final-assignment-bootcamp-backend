package nl.wondergem.wondercooks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;


@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    private String rolename;

@JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;


}
