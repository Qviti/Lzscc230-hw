package ge.homework.mapper;

import ge.homework.model.entity.CartItem;
import ge.homework.model.response.CartContentResp;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    default CartContentResp toCartContentResp(CartItem item) {
        return CartContentResp.builder()
            .id(item.getCart().getId())
            .foodId(item.getFood().getId())
            .foodName(item.getFood().getName())
            .quantity(item.getQuantity())
            .userId(item.getCart().getUser().getId())
            .build();
    }
    
    List<CartContentResp> toCartContentResp(List<CartItem> items);
}
