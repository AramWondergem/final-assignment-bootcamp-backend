package nl.wondergem.wondercooks.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
public class DeliveryDto {


    private long id;

    private boolean paid;

    private LocalTime ETA;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryDto that = (DeliveryDto) o;
        return id == that.id && paid == that.paid && Objects.equals(ETA, that.ETA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paid, ETA);
    }
}
