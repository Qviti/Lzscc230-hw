package ge.homework.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn
    private Order order;
    
    @ManyToOne
    private Food food;
    
    private int quantity;
    
    private long price;
    
    @Formula("quantity * price")
    private int total;
}
