package ge.homework.model.response;

import ge.homework.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentStatusResp {
    private long id;
    private PaymentStatus status;
}
