package duoc.projects.serena.services.EmotionalRegister;

import duoc.projects.serena.dto.emotionalRegister.EmotionalRegisterRequestDTO;
import duoc.projects.serena.dto.emotionalRegister.EmotionalRegisterResponseDTO;
import duoc.projects.serena.exception.ResourceNotFoundException;
import duoc.projects.serena.model.Emotion;
import duoc.projects.serena.model.EmotionalRegister;
import duoc.projects.serena.model.User;
import duoc.projects.serena.repository.EmotionRepository;
import duoc.projects.serena.repository.EmotionalRegisterRepository;
import duoc.projects.serena.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmotionalRegisterServiceImpl implements EmotionalRegisterService {

    private final EmotionalRegisterRepository repository;
    private final EmotionRepository emotionRepository;
    private final UserRepository userRepository;

    @Override
    public EmotionalRegisterResponseDTO create(EmotionalRegisterRequestDTO dto) {
        EmotionalRegister entity = new EmotionalRegister();

        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());

        Emotion emotion = emotionRepository.findById(dto.getEmotionId())
                .orElseThrow(() -> new ResourceNotFoundException("Emotion no encontrada con ID: " + dto.getEmotionId()));
        entity.setEmotion(emotion);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getUserId()));
        entity.setUser(user);

        repository.save(entity);
        return mapToResponse(entity);
    }

    @Override
    public EmotionalRegisterResponseDTO findById(Long id) {
        EmotionalRegister entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro emocional no encontrado con ID: " + id));
        return mapToResponse(entity);
    }

    @Override
    public List<EmotionalRegisterResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmotionalRegisterResponseDTO update(Long id, EmotionalRegisterRequestDTO dto) {
        EmotionalRegister entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro emocional no encontrado con ID: " + id));

        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());

        Emotion emotion = emotionRepository.findById(dto.getEmotionId())
                .orElseThrow(() -> new ResourceNotFoundException("Emotion no encontrada con ID: " + dto.getEmotionId()));
        entity.setEmotion(emotion);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getUserId()));
        entity.setUser(user);

        repository.save(entity);
        return mapToResponse(entity);
    }

    @Override
    public void delete(Long id) {
        EmotionalRegister entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro emocional no encontrado con ID: " + id));
        repository.delete(entity);
    }

    private EmotionalRegisterResponseDTO mapToResponse(EmotionalRegister entity) {
        EmotionalRegisterResponseDTO dto = new EmotionalRegisterResponseDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setDescription(entity.getDescription());
        dto.setEmotionId(entity.getEmotion().getId());
        dto.setUserId(entity.getUser().getId());
        return dto;
    }
}
