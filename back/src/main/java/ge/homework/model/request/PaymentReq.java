package ge.homework.model.request;

import lombok.Data;

@Data
public class PaymentReq {
    private long orderId;
    private String cardNumber;
    private String securityCode;
}
