package ge.homework.repository;

import ge.homework.model.entity.Payment;
import ge.homework.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByStatus(PaymentStatus paymentStatus);
}
