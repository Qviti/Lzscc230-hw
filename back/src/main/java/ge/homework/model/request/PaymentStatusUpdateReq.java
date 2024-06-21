package ge.homework.model.request;

import ge.homework.model.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentStatusUpdateReq {
    private long paymentId;
    private PaymentStatus status;
}
