package ge.homework.controller;


import ge.homework.model.request.AddFootToCartReq;
import ge.homework.model.response.CartContentResp;
import ge.homework.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    
    @PostMapping("/item/add")
    public List<CartContentResp> addFoodToCart(@RequestBody AddFootToCartReq req, @RequestHeader("Auth") String token) {
        return cartService.addFoodToCart(req, token);
    }
    @PutMapping("/item/edit")
    public List<CartContentResp> editFoodInCart(@RequestParam long foodId, @RequestParam int quantity, @RequestHeader("Auth") String token) {
        return cartService.editFoodInCart(foodId, quantity, token);
    }
    @DeleteMapping("/item/delete")
    public List<CartContentResp> deleteItem(@RequestParam long foodId, @RequestHeader("Auth") String token) {
        return cartService.deleteItem(foodId, token);
    }
    
    @GetMapping("/items")
    public List<CartContentResp> getItems(@RequestHeader("Auth") String token) {
        return cartService.getItems(token);
    }
}
