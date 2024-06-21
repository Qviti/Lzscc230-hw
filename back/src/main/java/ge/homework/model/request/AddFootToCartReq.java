package ge.homework.model.request;

import lombok.Data;

@Data
public class AddFootToCartReq {
    private int quantity;
    private long foodId;
}
