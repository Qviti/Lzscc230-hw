package ge.homework.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartContentResp {
    private long id;
    private long userId;
    private long foodId;
    private String foodName;
    private int quantity;
    private int total;
    private int price;
}
