package nl.wondergem.wondercooks.dto;

import lombok.Getter;
import lombok.Setter;
import nl.wondergem.wondercooks.model.Role;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
public class UserDtoSmall {
    public long id;
    public String email;
    public String username;
    public Collection<Role> roles;
    public String streetAndNumber;
    public String zipcode;
    public String city;
    public String favoriteColour;
    public String allergies;
    public String allergiesExplanation;
    public String profilePicture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDtoSmall that = (UserDtoSmall) o;
        return id == that.id && Objects.equals(email, that.email) && Objects.equals(username, that.username) && Objects.equals(roles, that.roles) && Objects.equals(streetAndNumber, that.streetAndNumber) && Objects.equals(zipcode, that.zipcode) && Objects.equals(city, that.city) && Objects.equals(favoriteColour, that.favoriteColour) && Objects.equals(allergies, that.allergies) && Objects.equals(allergiesExplanation, that.allergiesExplanation) && Objects.equals(profilePicture, that.profilePicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, roles, streetAndNumber, zipcode, city, favoriteColour, allergies, allergiesExplanation, profilePicture);
    }
}
