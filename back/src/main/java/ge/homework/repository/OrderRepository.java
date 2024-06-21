package ge.homework.repository;

import ge.homework.model.entity.Order;
import ge.homework.model.entity.User;
import ge.homework.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);

    List<Order> findAllByStatus(OrderStatus orderStatus);
}
