package ge.homework.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResp {
    private int quantity;
    private int total;
    private long price;
}
