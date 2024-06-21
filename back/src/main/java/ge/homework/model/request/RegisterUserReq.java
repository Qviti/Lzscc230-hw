package ge.homework.model.request;

import lombok.Data;

@Data
public class RegisterUserReq {
    private String username;
    private String password;
    private String fullName;
}
