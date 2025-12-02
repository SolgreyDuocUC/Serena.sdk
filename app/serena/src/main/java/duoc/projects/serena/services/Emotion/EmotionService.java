package duoc.projects.serena.services.Emotion;

import duoc.projects.serena.dto.emotion.EmotionRequestDTO;
import duoc.projects.serena.dto.emotion.EmotionResponseDTO;

import java.util.List;

public interface EmotionService {

    EmotionResponseDTO create(EmotionRequestDTO dto);

    EmotionResponseDTO findById(Long id);

    List<EmotionResponseDTO> findAll();

    EmotionResponseDTO update(Long id, EmotionRequestDTO dto);

    void delete(Long id);
}
