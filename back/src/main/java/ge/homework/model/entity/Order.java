package ge.homework.model.entity;

import ge.homework.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User user;
    
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "order")
    private List<OrderItem> items;
    
    private int total;
    
    private int itemCount;
    
    private OrderStatus status;
    
    public void updateTotal(int amount) {
        total += amount;
    }
    
    public void updateItemCount(int count) {
        itemCount += count;
    }
}
