package ge.homework.service;

import ge.homework.model.entity.Cart;
import ge.homework.model.entity.Food;
import ge.homework.model.entity.CartItem;
import ge.homework.model.entity.User;
import ge.homework.model.request.AddFootToCartReq;
import ge.homework.model.response.CartContentResp;
import ge.homework.repository.CartRepository;
import ge.homework.repository.FoodRepository;
import ge.homework.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final SessionService sessionService;
    private final FoodRepository foodRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public List<CartContentResp> addFoodToCart(AddFootToCartReq req, String token) {
        Food food = foodRepository.findById(req.getFoodId())
            .orElseThrow(() -> new RuntimeException("no such food"));
        User user = sessionService.getVerifiedUser(token);

        Cart cart = cartRepository.findByUser(user)
            .orElseGet(() -> {
                Cart temp = Cart.builder()
                    .user(user)
                    .build();
                return cartRepository.save(temp);
            });

        List<CartItem> selections = cartItemRepository.findAllByCartOrderById(cart);

        CartItem content = CartItem.builder()
            .cart(cart)
            .food(food)
            .quantity(req.getQuantity())
            .build();
        selections.add(content);
        
        cartItemRepository.save(content);
        
        return selections.stream()
            .map(e ->
                CartContentResp.builder()
                    .id(e.getId())
                    .foodId(e.getFood().getId())
                    .foodName(e.getFood().getName())
                    .quantity(e.getQuantity())
                    .userId(user.getId())
                    .total(e.getQuantity() * e.getFood().getPrice())
                    .price(e.getFood().getPrice())
                    .build()
            )
            .toList();

    }

    @Transactional
    public List<CartContentResp> deleteItem(long foodId, String token) {
        User user = sessionService.getVerifiedUser(token);
        Cart cart = cartRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("user has no cart"));
        
        cartItemRepository.deleteById(foodId);
        return cartItemRepository.findAllByCartOrderById(cart)
            .stream()
            .map(e ->
                CartContentResp.builder()
                    .id(e.getId())
                    .foodId(e.getFood().getId())
                    .foodName(e.getFood().getName())
                    .quantity(e.getQuantity())
                    .userId(user.getId())
                    .total(e.getQuantity() * e.getFood().getPrice())
                    .price(e.getFood().getPrice())
                    .build()
            )
            .toList();
    }

    @Transactional
    public List<CartContentResp> editFoodInCart(long foodId, int quantity, String token) {
        User user = sessionService.getVerifiedUser(token);
        Cart cart = cartRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("user has no cart"));

        CartItem item = cartItemRepository.findById(foodId)
            .orElseThrow(() -> new RuntimeException("item not in cart"));
        
        item.setQuantity(quantity);
        cartItemRepository.save(item);

        return cartItemRepository.findAllByCartOrderById(cart)
            .stream()
            .map(e ->
                CartContentResp.builder()
                    .id(e.getId())
                    .foodId(e.getFood().getId())
                    .foodName(e.getFood().getName())
                    .quantity(e.getQuantity())
                    .userId(user.getId())
                    .total(e.getQuantity() * e.getFood().getPrice())
                    .price(e.getFood().getPrice())
                    .build()
            )
            .toList();
    }

    public List<CartContentResp> getItems(String token) {
        User user = sessionService.getVerifiedUser(token);
        Cart cart = user.getCart();
        List<CartItem> items = cartItemRepository.findAllByCartOrderById(cart);

        return items.stream()
            .map(e ->
                CartContentResp.builder()
                    .id(e.getId())
                    .foodId(e.getFood().getId())
                    .foodName(e.getFood().getName())
                    .quantity(e.getQuantity())
                    .userId(user.getId())
                    .total(e.getQuantity() * e.getFood().getPrice())
                    .price(e.getFood().getPrice())
                    .build()
            )
            .toList();
    }
}
