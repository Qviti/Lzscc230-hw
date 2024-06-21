package ge.homework.model.request;

import lombok.Data;

@Data
public class AddCardToUserReq {
    private Long userId;
    private String cardNumber;
    private String cardSecurityCode;
}