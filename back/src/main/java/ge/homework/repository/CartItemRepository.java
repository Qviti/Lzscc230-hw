package ge.homework.repository;

import ge.homework.model.entity.Cart;
import ge.homework.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCart(Cart cart);

    List<CartItem> findAllByCartOrderById(Cart cart);

    Optional<CartItem> findByCartAndFoodId(Cart cart, long id);
    
    void removeByCart(Cart cart);

    void removeByCartAndFoodId(Cart cart, long foodId);
}
