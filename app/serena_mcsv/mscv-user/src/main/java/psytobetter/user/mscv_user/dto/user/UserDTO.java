package psytobetter.user.mscv_user.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private String userPassword;
    private Boolean userAceptConditions;
    private String userImageUri;

}
