package psytobetter.user.mscv_user.dto.user;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String userEmail;
    private String userPassword;
}
