package psytobetter.user.mscv_user.dto.user;

import lombok.Data;

@Data
public class UserCreateRequestDTO {
    private String userName;
    private String userEmail;
    private String userPassword;
    private Boolean userAceptConditions;
    private String userImageUri;
}
