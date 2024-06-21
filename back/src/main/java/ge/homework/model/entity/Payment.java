package ge.homework.model.entity;

import ge.homework.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private PaymentStatus status;
    
    @ManyToOne
    private Order order;
    
    @ManyToOne
    private Card card;
    
    private long amount;
}
