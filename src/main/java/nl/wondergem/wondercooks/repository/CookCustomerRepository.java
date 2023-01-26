package nl.wondergem.wondercooks.repository;

import nl.wondergem.wondercooks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookCustomerRepository extends JpaRepository<User, Long> {
}
