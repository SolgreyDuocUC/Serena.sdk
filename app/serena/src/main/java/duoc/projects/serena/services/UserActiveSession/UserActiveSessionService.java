package duoc.projects.serena.services.UserActiveSession;


import duoc.projects.serena.dto.userActiveSession.UserActiveSessionRequestDTO;
import duoc.projects.serena.dto.userActiveSession.UserActiveSessionResponseDTO;

import java.util.List;

public interface UserActiveSessionService {

    UserActiveSessionResponseDTO create(UserActiveSessionRequestDTO dto);

    UserActiveSessionResponseDTO findById(Integer sessionId);

    List<UserActiveSessionResponseDTO> findAll();

    UserActiveSessionResponseDTO update(Integer sessionId, UserActiveSessionRequestDTO dto);

    void delete(Integer sessionId);
}
