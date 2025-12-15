package psytobetter.user.mscv_user.service.UserActiveSession;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import psytobetter.user.mscv_user.dto.userActiveSession.UserActiveSessionRequestDTO;
import psytobetter.user.mscv_user.dto.userActiveSession.UserActiveSessionResponseDTO;
import psytobetter.user.mscv_user.exception.ResourceNotFoundException;
import psytobetter.user.mscv_user.model.UserActiveSession;
import psytobetter.user.mscv_user.repository.UserActiveSessionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActiveSessionServiceImpl implements UserActiveSessionService {

    private final UserActiveSessionRepository repository;

    @Override
    public UserActiveSessionResponseDTO create(UserActiveSessionRequestDTO dto) {
        UserActiveSession entity = new UserActiveSession();
        entity.setActiveUserId(dto.getActiveUserId());
        repository.save(entity);
        return mapToResponse(entity);
    }

    @Override
    public UserActiveSessionResponseDTO findById(Integer sessionId) {
        UserActiveSession entity = repository.findById(Long.valueOf(sessionId))
                .orElseThrow(() -> new ResourceNotFoundException("Session no encontrada con ID: " + sessionId));
        return mapToResponse(entity);
    }

    @Override
    public List<UserActiveSessionResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserActiveSessionResponseDTO update(Integer sessionId, UserActiveSessionRequestDTO dto) {
        UserActiveSession entity = repository.findById(Long.valueOf(sessionId))
                .orElseThrow(() -> new ResourceNotFoundException("Session no encontrada con ID: " + sessionId));

        entity.setActiveUserId(dto.getActiveUserId());
        repository.save(entity);

        return mapToResponse(entity);
    }

    @Override
    public void delete(Integer sessionId) {
        UserActiveSession entity = repository.findById(Long.valueOf(sessionId))
                .orElseThrow(() -> new ResourceNotFoundException("Session no encontrada con ID: " + sessionId));
        repository.delete(entity);
    }

    private UserActiveSessionResponseDTO mapToResponse(UserActiveSession entity) {
        UserActiveSessionResponseDTO dto = new UserActiveSessionResponseDTO();
        dto.setSessionId(Math.toIntExact(entity.getSessionId()));
        dto.setActiveUserId(entity.getActiveUserId());
        return dto;
    }
}
