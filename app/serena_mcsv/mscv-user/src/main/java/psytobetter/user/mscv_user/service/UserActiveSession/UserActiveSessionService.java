package psytobetter.user.mscv_user.service.UserActiveSession;

import psytobetter.user.mscv_user.dto.userActiveSession.UserActiveSessionRequestDTO;
import psytobetter.user.mscv_user.dto.userActiveSession.UserActiveSessionResponseDTO;

import java.util.List;

public interface UserActiveSessionService {

    UserActiveSessionResponseDTO create(UserActiveSessionRequestDTO dto);

    UserActiveSessionResponseDTO findById(Integer sessionId);

    List<UserActiveSessionResponseDTO> findAll();

    UserActiveSessionResponseDTO update(Integer sessionId, UserActiveSessionRequestDTO dto);

    void delete(Integer sessionId);
}
