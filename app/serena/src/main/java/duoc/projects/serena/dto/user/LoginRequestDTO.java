package duoc.projects.serena.dto.user;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String userEmail;
    private String userPassword;
}
