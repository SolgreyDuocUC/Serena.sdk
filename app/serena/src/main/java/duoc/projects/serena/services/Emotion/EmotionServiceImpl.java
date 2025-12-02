package duoc.projects.serena.services.Emotion;

import duoc.projects.serena.dto.emotion.EmotionRequestDTO;
import duoc.projects.serena.dto.emotion.EmotionResponseDTO;
import duoc.projects.serena.exception.EmotionNotFoundException;
import duoc.projects.serena.model.Emotion;
import duoc.projects.serena.repository.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmotionServiceImpl implements EmotionService {

    private final EmotionRepository emotionRepository;

    private EmotionResponseDTO mapToDTO(Emotion model) {
        EmotionResponseDTO dto = new EmotionResponseDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDescription(model.getDescription());
        dto.setColor(model.getColor());
        dto.setTextColor(model.getTextColor());
        dto.setIcon(model.getIcon());
        return dto;
    }

    @Override
    public EmotionResponseDTO create(EmotionRequestDTO dto) {
        Emotion emotion = new Emotion();
        emotion.setName(dto.getName());
        emotion.setDescription(dto.getDescription());
        emotion.setColor(dto.getColor());
        emotion.setTextColor(dto.getTextColor());
        emotion.setIcon(dto.getIcon());

        return mapToDTO(emotionRepository.save(emotion));
    }

    @Override
    public EmotionResponseDTO findById(Long id) {
        Emotion emotion = emotionRepository.findById(id)
                .orElseThrow(() -> new EmotionNotFoundException(id));
        return mapToDTO(emotion);
    }

    @Override
    public List<EmotionResponseDTO> findAll() {
        return emotionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public EmotionResponseDTO update(Long id, EmotionRequestDTO dto) {
        Emotion emotion = emotionRepository.findById(id)
                .orElseThrow(() -> new EmotionNotFoundException(id));

        emotion.setName(dto.getName());
        emotion.setDescription(dto.getDescription());
        emotion.setColor(dto.getColor());
        emotion.setTextColor(dto.getTextColor());
        emotion.setIcon(dto.getIcon());

        return mapToDTO(emotionRepository.save(emotion));
    }

    @Override
    public void delete(Long id) {
        Emotion emotion = emotionRepository.findById(id)
                .orElseThrow(() -> new EmotionNotFoundException(id));

        emotionRepository.delete(emotion);
    }
}
