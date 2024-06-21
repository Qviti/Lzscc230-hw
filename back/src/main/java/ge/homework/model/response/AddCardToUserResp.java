package ge.homework.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCardToUserResp {
    private Long id;
    private Long userId;
    private String cardNumber;
    private String cardSecurityCode;
}
