package J2EE_Bai6.repository;

import J2EE_Bai6.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}

