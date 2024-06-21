package ge.homework.model.entity;

import ge.homework.model.enums.FoodType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foods")
public class Food {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private FoodType category;
    
    private int price;
    
    private String name;
}
