package ge.homework.model.response;

import ge.homework.model.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResp {
    private long id;
    private int total;
    private int itemCount;
    private OrderStatus status;
    private List<OrderItemResp> items;
}
