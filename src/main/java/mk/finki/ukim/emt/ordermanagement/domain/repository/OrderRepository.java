package mk.finki.ukim.emt.ordermanagement.domain.repository;

import mk.finki.ukim.emt.ordermanagement.domain.models.Order;
import mk.finki.ukim.emt.ordermanagement.domain.models.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, OrderId> {
}
