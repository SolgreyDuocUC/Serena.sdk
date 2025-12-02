package duoc.projects.serena.services.UserActiveSession;

import duoc.projects.serena.dto.userActiveSession.UserActiveSessionRequestDTO;
import duoc.projects.serena.dto.userActiveSession.UserActiveSessionResponseDTO;
import duoc.projects.serena.exception.ResourceNotFoundException;
import duoc.projects.serena.model.UserActiveSession;
import duoc.projects.serena.repository.UserActiveSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
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
        dto.setSessionId(entity.getSessionId());
        dto.setActiveUserId(entity.getActiveUserId());
        return dto;
    }
}
