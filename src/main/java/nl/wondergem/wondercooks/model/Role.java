package nl.wondergem.wondercooks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;


public enum Role {
    ADMIN,
    COOK,
    USER


}
