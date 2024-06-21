package ge.homework.model.response;

import ge.homework.model.enums.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserResp {
    private Long id;
    private String username;
    private String fullName;
    private UserType userType;
    private Integer otp;
    private boolean verified;
}
