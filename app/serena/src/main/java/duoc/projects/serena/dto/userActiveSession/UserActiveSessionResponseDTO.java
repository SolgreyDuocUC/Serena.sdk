package duoc.projects.serena.dto.userActiveSession;

import lombok.Data;

@Data
public class UserActiveSessionResponseDTO {
    private Integer sessionId;
    private Long activeUserId;
}
