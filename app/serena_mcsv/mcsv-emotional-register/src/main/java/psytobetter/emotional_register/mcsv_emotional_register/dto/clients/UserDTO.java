package psytobetter.emotional_register.mcsv_emotional_register.dto.clients;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private Boolean userAceptConditions;
    private String userImageUri;
}
