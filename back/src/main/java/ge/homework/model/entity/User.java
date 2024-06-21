package ge.homework.model.entity;

import ge.homework.model.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    
    private String fullName;
    
    private UserType userType;
    
    private boolean verified;
    
    private String password;
    
    private Integer otp;
    
    @OneToOne(mappedBy = "user")
    private Cart cart;
}
