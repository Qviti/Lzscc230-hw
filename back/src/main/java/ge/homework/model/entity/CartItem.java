package ge.homework.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Cart cart;
    
    @ManyToOne
    private Food food;
    
    int quantity;
    
    @Transient
    int total;
    
    @PostLoad
    void calcTotal() {
        total = quantity * food.getPrice();
    }
}
