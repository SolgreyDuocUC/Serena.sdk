package duoc.projects.serena.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private Boolean userAceptConditions;
    private String userImageUri;
}
