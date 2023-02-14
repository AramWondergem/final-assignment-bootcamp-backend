package nl.wondergem.wondercooks.repository;

import nl.wondergem.wondercooks.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
