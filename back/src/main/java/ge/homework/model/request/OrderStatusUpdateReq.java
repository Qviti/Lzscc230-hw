package ge.homework.model.request;

import ge.homework.model.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusUpdateReq {
    private long orderId;
    private OrderStatus status;
}
